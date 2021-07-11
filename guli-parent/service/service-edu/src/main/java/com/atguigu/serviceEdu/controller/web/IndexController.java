package com.atguigu.serviceEdu.controller.web;

import com.atguigu.commonUtils.R;
import com.atguigu.serviceEdu.entity.EduCourse;
import com.atguigu.serviceEdu.entity.EduTeacher;
import com.atguigu.serviceEdu.service.EduCourseService;
import com.atguigu.serviceEdu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Super
 * @date 2020-04-08 11:20
 */
@Api(description = "首页")
@RestController
@RequestMapping("/web/serviceEdu/index")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class IndexController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("讲师和课程排序显示")
    @GetMapping("/getTeacherAndCourse")
    public R getTeacherAndCourse(){

        //查前8课程

        List<EduCourse> courseList = eduCourseService.listCourse();

        //查前4讲师
//        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
//        teacherQueryWrapper.orderByDesc("id");
//        teacherQueryWrapper.last("limit 4");
//        List<EduTeacher> teacherList = eduTeacherService.list(teacherQueryWrapper);

        List<EduTeacher> teacherList = eduTeacherService.listTeacher();
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
