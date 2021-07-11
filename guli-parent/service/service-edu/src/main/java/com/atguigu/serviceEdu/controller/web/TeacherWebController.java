package com.atguigu.serviceEdu.controller.web;

import com.atguigu.commonUtils.R;
import com.atguigu.serviceEdu.entity.EduCourse;
import com.atguigu.serviceEdu.entity.EduTeacher;
import com.atguigu.serviceEdu.service.EduCourseService;
import com.atguigu.serviceEdu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Super
 * @date 2020-03-18 14:51
 */
@Api(description = "web讲师管理")
@RestController
@RequestMapping("/web/serviceEdu/teacher")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class TeacherWebController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 注入课程service
     */
    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 讲师列表前端渲染
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation("web讲师列表前端渲染")
    @GetMapping("/getTeacherList/{page}/{limit}")
    public R webTeacherList(
                            @ApiParam(name = "page",value = "当前页",required = true)
                            @PathVariable Long page,
                            @ApiParam(name = "limit",value = "每页记录数",required = true)
                            @PathVariable Long limit
                            ){
        Page<EduTeacher> pageWebTeacher = new Page<>(page,limit);
        Map<String,Object> map = eduTeacherService.getWebTeacherList(pageWebTeacher);
        return R.ok().data(map);
    }


    /**
     * 根据讲师id查询讲师详情
     * @param teacherId
     * @return
     */
    @ApiOperation("web讲师详情")
    @GetMapping("/detail/{teacherId}")
    public R getTeacherDetail(
                              @ApiParam(name = "teacherId",value = "讲师id",required = true)
                              @PathVariable String teacherId
                              ){

        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        List<EduCourse> courseList = eduCourseService.getCourseByTeacherId(teacherId);
        return R.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }
}
