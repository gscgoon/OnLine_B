package com.atguigu.serviceEdu.service.impl;

import com.atguigu.serviceEdu.entity.EduTeacher;
import com.atguigu.serviceEdu.entity.vo.QueryTeacher;
import com.atguigu.serviceEdu.mapper.EduTeacherMapper;
import com.atguigu.serviceEdu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-03-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void getPage(Page<EduTeacher> pageT, QueryTeacher queryTeacher) {

        //默认升序排序
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        //查询对象是否为空
        if (queryTeacher == null) {
            baseMapper.selectPage(pageT,queryWrapper);
            return;
        }
        //得到查询对象的值
        String name = queryTeacher.getName();
        Integer level = queryTeacher.getLevel();
        String begin = queryTeacher.getBegin();
        String end = queryTeacher.getEnd();

        //拼接条件
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        baseMapper.selectPage(pageT,queryWrapper);
    }

    /**
     * 查讲师并将讲师添加到redis中
     * @return
     */
    @Cacheable(key = "'selectIndexList'",value = "teacher")
    @Override
    public List<EduTeacher> listTeacher() {

        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = baseMapper.selectList(teacherWrapper);
        return teacherList;
    }

    /**
     * web讲师列表
     * @param pageWebTeacher
     * @return
     */
    @Override
    public Map<String, Object> getWebTeacherList(Page<EduTeacher> pageWebTeacher) {

        //拼接条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //默认按排名查询
        wrapper.orderByAsc("sort");

        baseMapper.selectPage(pageWebTeacher, wrapper);
        List<EduTeacher> records = pageWebTeacher.getRecords();
        long total = pageWebTeacher.getTotal();
        long current = pageWebTeacher.getCurrent();
        long size = pageWebTeacher.getSize();
        long pages = pageWebTeacher.getPages();
        boolean hasNext = pageWebTeacher.hasNext();
        boolean hasPrevious = pageWebTeacher.hasPrevious();

        HashMap<String, Object> map = new HashMap<>();

        map.put("records",records);
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }
}
