package com.atguigu.serviceEdu.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.serviceEdu.entity.sort.OneSubject;
import com.atguigu.serviceEdu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-01
 */
@Api(description = "课程分类模块")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
@RestController
@RequestMapping("/admin/serviceEdu/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @ApiOperation("课程分类导入")
    @PostMapping("/addSubjectSort")
    public R getSubjectExcel(MultipartFile file){

        //获取excel数据
        eduSubjectService.analysisExcel(file,eduSubjectService);
        return R.ok();
    }

    @ApiOperation("课程分类列表")
    @GetMapping("/getAll")
    public R getAll(){
        //获取到的是一级分类嵌套二级分类的一级分类课程的集合
        List<OneSubject> list = eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }

}

