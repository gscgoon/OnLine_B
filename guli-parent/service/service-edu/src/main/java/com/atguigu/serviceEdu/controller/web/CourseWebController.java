package com.atguigu.serviceEdu.controller.web;

import com.atguigu.commonUtils.JwtUtils;
import com.atguigu.commonUtils.R;
import com.atguigu.commonUtils.vo.CourseDetailWebVoOrder;
import com.atguigu.serviceEdu.client.OrderClient;
import com.atguigu.serviceEdu.entity.EduCourse;
import com.atguigu.serviceEdu.entity.chapter.Chapter;
import com.atguigu.serviceEdu.entity.webVo.CourseDetailWebVo;
import com.atguigu.serviceEdu.entity.webVo.WebCourseQueryVo;
import com.atguigu.serviceEdu.service.EduChapterService;
import com.atguigu.serviceEdu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Super
 * @date 2020-04-10 12:42
 */
@Api(description = "web课程管理")
@RestController
@RequestMapping("/web/serviceEdu/course")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class CourseWebController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation("web课程列表前端渲染")
    @PostMapping("/getWebCourseList/{page}/{limit}")
    public R getWebCourseList(@ApiParam(name = "page", value = "当前页", required = true)
                              @PathVariable Long page,
                              @ApiParam(name = "limit", value = "每页记录数", required = true)
                              @PathVariable Long limit,
                              @RequestBody(required = false) WebCourseQueryVo webCourseQueryVo) {
        Page<EduCourse> pageWebCourse = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.getPageCourse(pageWebCourse, webCourseQueryVo);
        return R.ok().data(map);
    }

    //修改课程详情的方法，参数中添加request,用于取用户信息
    @ApiOperation("课程详情")
    @GetMapping("/getCourseDetail/{courseId}")
    public R getCourseDetail(@ApiParam(name = "courseId", value = "课程id", required = true)
                             @PathVariable String courseId, HttpServletRequest request
    ) {
        //课程信息和讲师信息
        CourseDetailWebVo courseDetailWebVo = eduCourseService.getDetailCourseById(courseId);

        //章节和小节信息
        List<Chapter> chapterList = eduChapterService.getAllChapter(courseId);

//        String user = JwtUtils.getMemberIdByJwtToken(request);
        //是否登录
        if (!StringUtils.isEmpty(JwtUtils.getMemberIdByJwtToken(request))) {
            //登录情况
            //远程调用order服务，查看课程是否被购买
            boolean courseIsBuy = orderClient.courseIsBuy(courseId, JwtUtils.getMemberIdByJwtToken(request));

            return R.ok().data("course", courseDetailWebVo).data("chapterList", chapterList).data("courseIsBuy", courseIsBuy);
        } else {
            //未登录情况
            return R.ok().data("course",courseDetailWebVo).data("chapterList",chapterList);
        }

    }


    @ApiOperation("根据id查订单课程信息")
    @GetMapping("/getCourseInfoOrder/{id}")
    public CourseDetailWebVoOrder getCourseInfoOrder(@ApiParam(name = "id", value = "课程id", required = true)
                                                     @PathVariable String id
    ) {
        CourseDetailWebVoOrder courseDetailWebVoOrder = new CourseDetailWebVoOrder();
        CourseDetailWebVo eduCourse = eduCourseService.getDetailCourseById(id);

        BeanUtils.copyProperties(eduCourse, courseDetailWebVoOrder);
        return courseDetailWebVoOrder;

    }
}
