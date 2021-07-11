package com.atguigu.serviceEdu.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.serviceEdu.entity.EduChapter;
import com.atguigu.serviceEdu.entity.chapter.Chapter;
import com.atguigu.serviceEdu.service.EduChapterService;
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
@Api(description = "课程大纲")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
@RestController
@RequestMapping("/admin/serviceEdu/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 章节和小节
     * @param courseId
     * @return
     */
    @ApiOperation("获取某类课程的所有章节和小节")
    @GetMapping("/getAll/{courseId}")
    public R getAllChapter(@ApiParam(name = "courseId",value = "课程id",required = true)
                            @PathVariable String courseId
                           ){
        //返回章节集合到页面中
        List<Chapter> chapterList =  eduChapterService.getAllChapter(courseId);
        return R.ok().data("chapterList",chapterList);
    }


    /**
     * 添加章节信息
     * @param eduChapter
     * @return
     */
    @ApiOperation("添加章节")
    @PostMapping("/add")
    public R addChapter(@ApiParam(name = "chapter",value = "章节对象",required = true)
                        @RequestBody EduChapter eduChapter
                        ){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 查章节信息
     * @param id
     * @return
     */
    @ApiOperation("根据章节id查章节")
    @GetMapping("/getChapter/{id}")
    public R getChapterById(@ApiParam(name = "id",value = "章节id",required = true)
                            @PathVariable String id
                            ){
        EduChapter chapter = eduChapterService.getById(id);
        return R.ok().data("chapter",chapter);
    }

    /**
     * 修改章节信息
     * @param eduChapter
     * @return
     */
    @ApiOperation("修改章节")
    @PostMapping("/updateChapter")
    public R getChapterById(@ApiParam(name = "chapter",value = "章节对象",required = true)
                            @RequestBody EduChapter eduChapter
                            ){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("/deleteChapter/{id}")
    public R deleteChapterById(@ApiParam(name = "id",value = "章节id",required = true)
                                @PathVariable String id
                                ){
        boolean flag = eduChapterService.deleteChapterById(id);
        if(flag){
            return R.ok().message("章节删除成功！");
        }else {
            return R.error().message("章节删除失败！");
        }
    }
}

