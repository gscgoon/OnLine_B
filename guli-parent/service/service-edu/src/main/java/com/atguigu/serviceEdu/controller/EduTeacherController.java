package com.atguigu.serviceEdu.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.serviceEdu.entity.EduTeacher;
import com.atguigu.serviceEdu.entity.vo.QueryTeacher;
import com.atguigu.serviceEdu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-03-29
 */
//swagger
@Api(description = "讲师模块")
//解决跨域
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
@RestController
@RequestMapping("/admin/serviceEdu/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 用于返回到前端添加课程信息的讲师下拉中
     * @return
     */
    @ApiOperation("无分页讲师列表")
    @GetMapping("/getAll")
    public R getAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation("讲师列表")
    @PostMapping("/getList/{page}/{limit}")
    public R getList(@ApiParam(name = "page",value = "当前页",required = true)
                      @PathVariable Long page,
                     @ApiParam(name = "limit",value = "每页记录数",required = true)
                     @PathVariable Long limit,
                     @ApiParam(name = "queryTeacher",value = "讲师查询对象",required = false)
                     @RequestBody(required = false) QueryTeacher queryTeacher
                     ){
        Page<EduTeacher> pageT = new Page<>(page,limit);
        eduTeacherService.getPage(pageT,queryTeacher);
        long total = pageT.getTotal();
        List<EduTeacher> records = pageT.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    @ApiOperation("添加讲师")
    @PostMapping("/add")
    public R addTeacher(
            @ApiParam(name = "teacher",value = "讲师json对象",required = true)
            @RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok().message("讲师添加成功！");
        }else {
            return R.error().message("讲师添加失败！");
        }
    }

    @ApiOperation("根据讲师id删除讲师")
    @DeleteMapping("/delete/{id}")
    public R delTeacher(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable String id ){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok().message("删除讲师成功！");
        }else {
            return R.error().message("删除讲师失败！");
        }
    }

    @ApiOperation("修改讲师")
    @PostMapping("/update")
    public R updateTeacher(@ApiParam(name = "teacher",value = "要修改的讲师对象",required = true)
                           @RequestBody EduTeacher eduTeacher
    ){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if(update){
            return R.ok().message("讲师修改成功！");
        }else {
            return R.error().message("讲师修改失败！");
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("/query/{id}")
    public R queryTeacher(@ApiParam(name = "id",value = "要查询的讲师id",required = true)
                          @PathVariable String id
                          ){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }
}

