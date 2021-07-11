package com.atguigu.serviceUserCenter.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.commonUtils.vo.UserCenterMemberCommentVo;
import com.atguigu.serviceUserCenter.entity.UserCenterMember;
import com.atguigu.serviceUserCenter.entity.vo.LoginVo;
import com.atguigu.serviceUserCenter.entity.vo.RegisterVo;
import com.atguigu.serviceUserCenter.service.UserCenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-08
 */
@Api(description = "会员模块")
@RestController
@RequestMapping("/admin/serviceUserCenter/user-center-member")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class UserCenterMemberController {

    @Autowired
    private UserCenterMemberService userCenterMemberService;

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public R register(@ApiParam(name = "registerVo", value = "注册对象registerVo", required = true)
                    @RequestBody RegisterVo registerVo
    ) {
        boolean flag = userCenterMemberService.registMember(registerVo);
        if (flag) {
//            return R.ok().message("会员注册成功！");
            return R.ok();
        } else {
//            return R.error().message("会员注册失败！");
            return R.error();
        }
    }

    @ApiOperation("会员登录")
    @PostMapping("/login")
    public R login(@ApiParam(name = "loginVo", value = "会员登录loginVo", required = true)
                   @RequestBody LoginVo loginVo
    ) {
        //返回token
        String token = userCenterMemberService.login(loginVo);
        return R.ok().data("token", token);
    }

//    @ApiOperation("登录统计")
//    @GetMapping("/countLogin/{day}")
//    public R countLogin(@ApiParam(name = "day", value = "统计日期day", required = true)
//                        @PathVariable String day
//    ) {
//        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
//        wrapper.eq("substring('gmt_create',0,10)", day);
//        int count = ucenterMemberService.count(wrapper);
//        if (count > 0) {
//            return R.ok().data("count", count);
//        } else {
//            return R.error().message("今日没有人登录");
//        }
//    }


    @ApiOperation("统计注册")
    @GetMapping("/countRegister/{day}")
    public R countRegister(@ApiParam(name = "day",value = "会员统计日期",required = true)
                         @PathVariable String day
                         ){
        Integer count = userCenterMemberService.countRegister(day);
        return R.ok().data("count",count);
    }

    @ApiOperation("用户信息")
    @GetMapping("/getMemberById/{id}")
    public UserCenterMemberCommentVo getLoginInfo(@PathVariable String id)
    {
        UserCenterMember userCenterMember = userCenterMemberService.getById(id);
        UserCenterMemberCommentVo userCenterMemberCommentVo =  new UserCenterMemberCommentVo();
        BeanUtils.copyProperties(userCenterMember,userCenterMemberCommentVo);
        return userCenterMemberCommentVo;
    }
}

