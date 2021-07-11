package com.atguigu.serviceOrder.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.serviceOrder.service.TPayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-11
 */
@Api(description = "支付模块")
@RestController
@RequestMapping("/web/serviceOrder/tPayLog")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class TPayLogController {

    @Autowired
    private TPayLogService tPayLogService;

    @ApiOperation("根据订单号orderNo，生成二维码")
    @GetMapping("/getTwoBarCodes/{orderNo}")
    public R payOrder(@ApiParam(name = "orderNo",value = "订单号",required = true)
                      @PathVariable String orderNo
                      ){
        //返回二维码信息，使用map封装信息
        Map map = tPayLogService.payOrder(orderNo);
        return R.ok().data(map);
    }


    @ApiOperation("根据订单号，查询订单的状态")
    @GetMapping("/checkOrderStatus/{orderNo}")
    public R checkOrderStatus(@ApiParam(name = "orderNo",value = "订单号",required = true)
                              @PathVariable String orderNo
                              ){
        //订单的支付状态查询
        Map<String,String> map = tPayLogService.checkOrderStatus(orderNo);
        if(map == null){
            return R.error().message("支付异常");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            //更新订单状态
            tPayLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        //否则正在支付,匹配前端拦截器的code值，如果code = 25000,不进行提示
        return R.ok().code(25000).message("支付中");
    }
}

