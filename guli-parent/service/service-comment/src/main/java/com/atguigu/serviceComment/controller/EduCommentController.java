package com.atguigu.serviceComment.controller;


import com.atguigu.commonUtils.JwtUtils;
import com.atguigu.commonUtils.R;
import com.atguigu.serviceComment.entity.EduComment;
import com.atguigu.serviceComment.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-20
 */
@Api(description = "课程评论功能")
@RestController
@RequestMapping("/web/serviceComment/eduComment")
//@CrossOrigin
public class EduCommentController
{

    @Autowired
    private EduCommentService eduCommentService;

    @ApiOperation("所有评论")
    @GetMapping("/getAllComments/{page}/{limit}/{courseId}")
    public R getAllComments(@PathVariable Long page,@PathVariable Long limit,@PathVariable String courseId)
    {
        Page<EduComment> pageComment = new Page<>(page,limit);
        Map map = eduCommentService.getComments(pageComment,courseId);
        return R.ok().data(map);
    }

    @ApiOperation("添加评论")
    @PostMapping("/addComment")
    public R addComment(@ApiParam(name = "comment",value = "评论对象",required = true) @RequestBody EduComment eduComment,HttpServletRequest request)
    {
        //获取token中的用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId))
        {
            return R.error().code(28004).message("您好，请先登录在进行评论！");
        }else {
            boolean flag = eduCommentService.addComment(eduComment,memberId);
            if(flag)
            {
                return R.ok();
            }else
            {
                return R.error();
            }
        }
    }
}
