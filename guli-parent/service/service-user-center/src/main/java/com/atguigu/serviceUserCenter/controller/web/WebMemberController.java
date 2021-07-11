package com.atguigu.serviceUserCenter.controller.web;

import com.atguigu.commonUtils.JwtUtils;
import com.atguigu.commonUtils.R;
import com.atguigu.commonUtils.vo.UserCenterMemberOrderVo;
import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceUserCenter.entity.UserCenterMember;
import com.atguigu.serviceUserCenter.service.UserCenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Super
 * @date 2020-04-09 1:09
 */
@Api(description = "获取登录信息")
@RestController
@RequestMapping("/web/serviceUserCenter/member")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class WebMemberController {

    @Autowired
    private UserCenterMemberService userCenterMemberService;

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("/auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            UserCenterMember userCenterMember = userCenterMemberService.getLoginInfo(memberId);
            return R.ok().data("item", userCenterMember);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"获取登录信息失败！");
        }
    }

    @ApiOperation("根据id查订单用户详情")
    @GetMapping("/getMemberOrder/{id}")
    public UserCenterMemberOrderVo getMemberInfoOrder(@ApiParam(name = "id",value = "用户id",required = true)
                                                   @PathVariable String id
                                                   ){
        UserCenterMemberOrderVo userCenterMemberOrderVo = new UserCenterMemberOrderVo();
        UserCenterMember userCenterMember = userCenterMemberService.getById(id);
        BeanUtils.copyProperties(userCenterMember,userCenterMemberOrderVo);
        return userCenterMemberOrderVo;

    }
}
