package com.atguigu.serviceEdu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.serviceEdu.entity.EduSubject;
import com.atguigu.serviceEdu.entity.excel.ExcelSubjectData;
import com.atguigu.serviceEdu.entity.sort.OneSubject;
import com.atguigu.serviceEdu.entity.sort.TwoSubject;
import com.atguigu.serviceEdu.listener.SubjectExcelListener;
import com.atguigu.serviceEdu.mapper.EduSubjectMapper;
import com.atguigu.serviceEdu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 课程分类导入
     * @param file
     * @param eduSubjectService
     * @return
     */
    @Override
    public void analysisExcel(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            //获取文件file,使用easyExcel解析课程分类的excel
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream,ExcelSubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 课程分类列表
     * @return
     */
    @Override
    public List<OneSubject> getAllSubject() {

        //将数据封装为页面展示的数据结构的数据类型，一级分类中包含多个二级分类
        //1.查询一级分类课程(parent_id = 0 即为一级分类)
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneQueryGetList = baseMapper.selectList(oneWrapper);

        //2.查询二级分类课程
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoQueryGetList = baseMapper.selectList(twoWrapper);

        //定义一个集合，用于存放最终要放回的集合，其实就是存OneSubject 的集合
        ArrayList<OneSubject> oneSubjectListFinal = new ArrayList<>();

        //3.封装一级分类课程
        //遍历查询到的一级分类课程
        for (int i = 0; i < oneQueryGetList.size(); i++) {

            EduSubject eduSubjectOne = oneQueryGetList.get(i);
            OneSubject oneSubject = new OneSubject();
            //使用beanUtils工具类拷贝属性
            BeanUtils.copyProperties(eduSubjectOne,oneSubject);
            //将一级分类课程添加到oneSubjectListFinal集合中
            oneSubjectListFinal.add(oneSubject);

            //4.将二级分类课程封装到一级分类中
            //定义存放二级分类课程的集合
            ArrayList<TwoSubject> twoList = new ArrayList<>();
            //遍历获取到的二级分类课程
            for (int j = 0; j <twoQueryGetList.size() ; j++) {
                EduSubject eduSubjectTwo = twoQueryGetList.get(j);
                //封装时需要判断parent_id和一级分类id是否相同，相同就添加
                if(eduSubjectTwo.getParentId().equals(eduSubjectOne.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubjectTwo,twoSubject);
                    twoList.add(twoSubject);
                }
            }
            //设置一级分类课程的children的属性
            oneSubject.setChildren(twoList);

        }

        //5.返回最终封装好的数据集合
        return oneSubjectListFinal;
    }
}
