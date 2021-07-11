package com.atguigu.serviceEdu.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.serviceEdu.entity.EduCourse;
import com.atguigu.serviceEdu.entity.vo.CourseInfoForm;
import com.atguigu.serviceEdu.entity.vo.CoursePublishVo;
import com.atguigu.serviceEdu.entity.vo.CourseQuery;
import com.atguigu.serviceEdu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-02
 */
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
@Api(description = "课程管理")
@RestController
@RequestMapping("/admin/serviceEdu/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("添加课程")
    @PostMapping("/add")
    public R addCourse(@ApiParam(name = "",value = "课程对象",required = true)
                       @RequestBody CourseInfoForm courseInfoForm
                       ){
        /**
         * 修改方法，为后续修改课程提供课程courseId   eduCourseService.addCourse(courseInfoForm)
         */
        String courseId = eduCourseService.addCourse(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }

    /**
     * 课程信息用于数据回显
     * @param id
     * @return
     */
    @ApiOperation("根据课程id，获取课程信息")
    @GetMapping("/getCourseById/{id}")
    public R getCourseInfoForm(@ApiParam(name ="id",value ="课程id" ,required = true)
                                            @PathVariable String id
                                            ){
        CourseInfoForm courseInfoForm = eduCourseService.getCourseForm(id);
        return R.ok().data("courseInfoForm",courseInfoForm);
    }

    @ApiOperation("修改课程信息")
    @PostMapping("/updateCourse")
    public R updateCourse(@ApiParam(name = "courseForm",value = "课程对象",required = true)
                          @RequestBody CourseInfoForm courseInfoForm
                          ){
        Boolean flag = eduCourseService.updateCourse(courseInfoForm);
        if(flag){
            return R.ok().message("课程信息修改成功！");
        }else {
            return R.error().message("课程信息修改失败！");
        }
    }

    @ApiOperation("获取要发布课程的基础信息")
    @GetMapping("/getCoursePublish/{id}")
    public R getPublishCourse(@ApiParam(name = "courseId",value = "发布课程的id",required = true)
                              @PathVariable String id
                              ){
        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourse(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @ApiOperation("发布课程")
    @GetMapping("/publish/{id}")
    public R publishCourse(@ApiParam(name = "id",value = "要发布的课程id",required = true)
                           @PathVariable String id
                           ){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     * 分页查询课程
     * @param page
     * @param limit
     * @param courseQuery
     * @return
     */
    @ApiOperation(value = "分页课程列表")
    @PostMapping("/query/{page}/{limit}")
    public R queryCourse(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false)CourseQuery courseQuery){

        Page<EduCourse> coursePageParam = new Page<>(page, limit);

        eduCourseService.pageQueryCourse(coursePageParam, courseQuery);
        List<EduCourse> records = coursePageParam.getRecords();
        long total = coursePageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }


    @ApiOperation("删除课程")
    @DeleteMapping("/delete/{id}")
    public R deleteCourse(@ApiParam(name = "id",value = "课程id",required = true)
                          @PathVariable String id
                          ){
        boolean flag = eduCourseService.deleteCourseById(id);
        if(flag){
            return R.ok().message("课程删除成功！");
        }else {
            return R.error().message("课程删除失败！");
        }
    }
}

