package com.atguigu.serviceEdu.service;

import com.atguigu.serviceEdu.entity.EduCourse;
import com.atguigu.serviceEdu.entity.vo.CourseInfoForm;
import com.atguigu.serviceEdu.entity.vo.CoursePublishVo;
import com.atguigu.serviceEdu.entity.vo.CourseQuery;
import com.atguigu.serviceEdu.entity.webVo.CourseDetailWebVo;
import com.atguigu.serviceEdu.entity.webVo.WebCourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-02
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程
     */
    String addCourse(CourseInfoForm courseInfoForm);

    /**
     * 课程信息回显
     * @param id
     * @return
     */
    CourseInfoForm getCourseForm(String id);

    /**
     * 修改课程信息
     * @param courseInfoForm
     * @return
     */
    Boolean updateCourse(CourseInfoForm courseInfoForm);

    /**
     * 获取发布课程的基础信息
     * @param id
     * @return
     */
    CoursePublishVo getPublishCourse(String id);


    /**
     * 课程列表查询
     * @param coursePageParam
     * @param courseQuery
     */
    void pageQueryCourse(Page<EduCourse> coursePageParam, CourseQuery courseQuery);

    /**
     * 删除课程
     * @param id
     * @return
     */
    boolean deleteCourseById(String id);

    /**
     * 查课程
     * @return
     */
    List<EduCourse> listCourse();


    /**
     * web讲师详情的课程
     * @param teacherId
     * @return
     */
    List<EduCourse> getCourseByTeacherId(String teacherId);

    /**
     * web课程列表
     * @param pageWebCourse
     * @param webCourseQueryVo
     * @return
     */
    Map<String,Object> getPageCourse(Page<EduCourse> pageWebCourse, WebCourseQueryVo webCourseQueryVo);

    /**
     * web课程详情
     * @param courseId
     * @return
     */
    CourseDetailWebVo getDetailCourseById(String courseId);
}
