package com.atguigu.serviceOrder.controller;


import com.atguigu.commonUtils.JwtUtils;
import com.atguigu.commonUtils.R;
import com.atguigu.serviceOrder.service.TOrderService;
import com.atguigu.serviceOrder.entity.TOrder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-11
 */
@Api(description = "订单模块")
@RestController
@RequestMapping("/web/serviceOrder/tOrder")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class TOrderController {

    @Autowired
    private TOrderService tOrderService;

    /**
     * 生成订单时，需要课程id，用户id，而用户信息是存放在cookie中，所以需要传入request对象
     * @param courseId
     * @param request
     * @return R
     */
    @ApiOperation("add订单")
    @GetMapping("/addOrderByCourseId/{courseId}")
    public R addOrder(@ApiParam(name = "courseId",value = "课程id",required = true)
                      @PathVariable String courseId, HttpServletRequest request
    ){

        //通过jwt工具类得到用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //返回订单号用于在页面显示订单信息
        String orderNo = tOrderService.addOrderByCourseId(courseId,memberId);
        return R.ok().data("orderNo",orderNo);
    }

    @ApiOperation("根据订单号查订单详情")
    @GetMapping("/getOrder/{orderNo}")
    public R getOrderInfo(@ApiParam(name = "orderNo",value = "订单号",required = true)
                          @PathVariable String orderNo
    ){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = tOrderService.getOne(wrapper);
        return R.ok().data("order",order);
    }

    @ApiOperation("根据课程id，会员id，订单状态，查订单")
    @GetMapping("/courseIsBuy/{courseId}/{memberId}")
    public boolean courseIsBuy(@ApiParam(name = "courseId",value = "课程id",required = true)
                                @PathVariable String courseId,
                               @ApiParam(name = "memberId",value = "会员id",required = true)
                               @PathVariable String memberId
                               ){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        //直接返回true/false
        int count = tOrderService.count(wrapper);
        return count > 0 ;
    }

}

