package com.atguigu.serviceEdu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceEdu.entity.EduSubject;
import com.atguigu.serviceEdu.entity.excel.ExcelSubjectData;
import com.atguigu.serviceEdu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author Super
 * @date 2020-04-01 11:36
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {


    /**
     * 手动注入EduSubjectService，用于操作数据库
     */
    public EduSubjectService eduSubjectService;
    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService eduSubjectService){
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 处理excel
     * @param excelSubjectData
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {

        //判断excel是否为空
        if(excelSubjectData == null){
            //抛出自定义异常
            throw new GuliException(20001,"数据不能为空！");
        }else {
            //添加一级分类,不能重复
            EduSubject oneExist = this.oneSubjectSort(eduSubjectService, excelSubjectData.getOneSubjectSort());
            if(oneExist == null){
                //这里直接使用查到的对象即可，不用new,否则出错：二级分类找不到parentId,导致空指针异常
                oneExist= new EduSubject();
                oneExist.setTitle(excelSubjectData.getOneSubjectSort());
                oneExist.setParentId("0");
                eduSubjectService.save(oneExist);
            }

            //添加二级分类，不能重复
            //获取一级分类id
            String parentId = oneExist.getId();
            EduSubject twoExist = this.twoSubjectSort(eduSubjectService, excelSubjectData.getTwoSubjectSort(), parentId);
            if(twoExist == null){
                twoExist = new EduSubject();
                twoExist.setTitle(excelSubjectData.getTwoSubjectSort());
                twoExist.setParentId(parentId);
                eduSubjectService.save(twoExist);
            }
        }
    }

    /**
     * 判断一级分类是否存在
     * @param eduSubjectService
     * @param name
     * @return
     */
    private EduSubject oneSubjectSort(EduSubjectService eduSubjectService,String name){
        //条件构造器
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        //parent_id = 0 表示为一级分类
        queryWrapper.eq("parent_id","0");
        EduSubject one = eduSubjectService.getOne(queryWrapper);
        return one;
    }

    /**
     * 判断二级分类是否存在
     * @param eduSubjectService
     * @param name
     * @param parentId
     * @return
     */
    private EduSubject twoSubjectSort(EduSubjectService eduSubjectService,String name,String parentId){
        //条件构造器
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        //parent_id = 0 表示为一级分类
        queryWrapper.eq("parent_id",parentId);
        EduSubject two = eduSubjectService.getOne(queryWrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
