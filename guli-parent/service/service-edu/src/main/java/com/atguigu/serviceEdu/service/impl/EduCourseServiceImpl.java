package com.atguigu.serviceEdu.service.impl;

import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceEdu.entity.EduCourse;
import com.atguigu.serviceEdu.entity.EduCourseDescription;
import com.atguigu.serviceEdu.entity.vo.CourseInfoForm;
import com.atguigu.serviceEdu.entity.vo.CoursePublishVo;
import com.atguigu.serviceEdu.entity.vo.CourseQuery;
import com.atguigu.serviceEdu.entity.webVo.CourseDetailWebVo;
import com.atguigu.serviceEdu.entity.webVo.WebCourseQueryVo;
import com.atguigu.serviceEdu.mapper.EduCourseMapper;
import com.atguigu.serviceEdu.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    /**
     * 注入eduCourseDescription的service用于操作EduCourseDescription的数据库
     */
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 注入章节的service
     */
    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 注入小节的service
     */
    @Autowired
    private EduVideoService eduVideoService;


    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 添加课程
     */
    @Override
    public String addCourse(CourseInfoForm courseInfoForm) {

        //需要象两张表中添加数据，1-课程表 2-课程简介表

        //1-课程表添加数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        //判断返回的成功条数
        if(insert <= 0){
            throw new GuliException(20001,"添加课程失败！");
        }

        //获取课程表的id用于添加到简介表中的id，保持课程和简介一对一
        String cid = eduCourse.getId();

        /**
         * 2 -课程简介表添加数据,由于课程和简介是一对一的关系，在添加时，会出现课程表的id和简介表的id不一致的问题
         * 解决方案：将EduCourseDescription类中的id策略进行修改 type = IdType.ID_WORKER_STR  改为：type = IdType.INPUT
         */
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(cid);
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        //判断添加操作返回的Boolean类型
        if(!save){
            throw new GuliException(20001,"添加课程简介失败！");
        }
        //返回课程的id
        return cid;
    }

    /**
     * 课程信息回显
     * @param id
     * @return
     */
    @Override
    public CourseInfoForm getCourseForm(String id) {

        //查课程信息
        EduCourse eduCourse = baseMapper.selectById(id);

        //查课程简介
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);

        //数据转换
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        courseInfoForm.setDescription(eduCourseDescription.getDescription());
        //返回结果
        return courseInfoForm;
    }

    /**
     * 修改课程信息
     * @param courseInfoForm
     * @return
     */
    @Override
    public Boolean updateCourse(CourseInfoForm courseInfoForm) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        //修改课程表
        int eduCourseCount = baseMapper.updateById(eduCourse);
        if(eduCourseCount <= 0){
            throw new GuliException(20001,"修改课程表失败！");
        }
        //修改简介表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(courseInfoForm.getId());
        boolean update = eduCourseDescriptionService.updateById(eduCourseDescription);
        if(!update){
            throw new GuliException(20001,"修改简介表失败！");
        }

        if(eduCourseCount > 0 && update){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取发布课程的基础信息
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishVo getPublishCourse(String courseId) {
        //调用自定义的mapper方法
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishById(courseId);
        return coursePublishVo;
    }

    /**
     * 课程列表查询
     * @param coursePageParam
     * @param courseQuery
     */
    @Override
    public void pageQueryCourse(Page<EduCourse> coursePageParam, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapperCourse = new QueryWrapper<>();
        queryWrapperCourse.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(coursePageParam, queryWrapperCourse);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapperCourse.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapperCourse.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapperCourse.eq("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapperCourse.eq("subject_id", subjectId);
        }

        baseMapper.selectPage(coursePageParam, queryWrapperCourse);

    }

    /**
     * 删除课程
     * @param id
     * @return
     */
    @Override
    public boolean deleteCourseById(String id) {

        //删除课程时，直接将课程下包含的章节，小节，以及云端的视频一块进行删除

        //删除小节
        eduVideoService.deleteVideoByCourseId(id);

        //删除章节
        eduChapterService.deleteChapterByCourseId(id);

        //删除描述
        eduCourseDescriptionService.removeById(id);

        //删除课程
        Integer count = baseMapper.deleteById(id);

        return count > 0;


    }

    /**
     * 查课程并将课程数据保存到redis中
     * @return
     */
    @Cacheable(key = "'selectIndexList'",value = "course")
    @Override
    public List<EduCourse> listCourse() {

        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> eduCourse = baseMapper.selectList(courseWrapper);
        return eduCourse;
    }

    /**
     * web讲师详情的课程
     * @param teacherId
     * @return
     */
    @Override
    public List<EduCourse> getCourseByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = baseMapper.selectList(courseQueryWrapper);
        return courseList;
    }

    /**
     * web课程列表
     * @param pageWebCourse
     * @param webCourseQueryVo
     * @return
     */
    @Override
    public Map<String, Object> getPageCourse(Page<EduCourse> pageWebCourse, WebCourseQueryVo webCourseQueryVo) {

        //拼接条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(webCourseQueryVo == null){
            baseMapper.selectPage(pageWebCourse, wrapper);
        }
        if(!StringUtils.isEmpty(webCourseQueryVo.getTeacherId())){
            wrapper.eq("teacher_id",webCourseQueryVo.getTeacherId());
        }
        if(!StringUtils.isEmpty(webCourseQueryVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count",webCourseQueryVo.getBuyCountSort());
        }
        if(!StringUtils.isEmpty(webCourseQueryVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create",webCourseQueryVo.getGmtCreateSort());
        }
        if(!StringUtils.isEmpty(webCourseQueryVo.getPriceSort())){
            wrapper.orderByDesc("price",webCourseQueryVo.getPriceSort());
        }
        if(!StringUtils.isEmpty(webCourseQueryVo.getSubjectId())){
            wrapper.eq("subject_id",webCourseQueryVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(webCourseQueryVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",webCourseQueryVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(webCourseQueryVo.getTitle())){
            wrapper.eq("title",webCourseQueryVo.getTitle());
        }


        baseMapper.selectPage(pageWebCourse, wrapper);
        List<EduCourse> records = pageWebCourse.getRecords();
        long total = pageWebCourse.getTotal();
        long current = pageWebCourse.getCurrent();
        long size = pageWebCourse.getSize();
        long pages = pageWebCourse.getPages();
        boolean hasNext = pageWebCourse.hasNext();
        boolean hasPrevious = pageWebCourse.hasPrevious();

        HashMap<String, Object> map = new HashMap<>();

        map.put("records", records);
        map.put("total", total);
        map.put("current", current);
        map.put("size", size);
        map.put("pages", pages);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    /**
     * web课程详情
     * @param courseId
     * @return
     */
    @Override
    public CourseDetailWebVo getDetailCourseById(String courseId) {

        //使用自定义的mapper方法
        CourseDetailWebVo courseDetailWebVo = baseMapper.getCourseDetailByCourseId(courseId);
        return courseDetailWebVo;
    }
}
