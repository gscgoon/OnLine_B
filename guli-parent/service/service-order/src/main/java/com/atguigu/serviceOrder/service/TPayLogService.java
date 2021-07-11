package com.atguigu.serviceOrder.service;

import com.atguigu.serviceOrder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-11
 */
public interface TPayLogService extends IService<TPayLog> {

    /**
     * 生成二维码
     * @param orderNo
     */
    Map payOrder(String orderNo);

    /**
     * 订单支付状态查询
     * @param orderNo
     * @return
     */
    Map<String,String> checkOrderStatus(String orderNo);

    /**
     * 修改订单状态，并向支付表中添加支付数据
     * @param map
     */
    void updateOrderStatus(Map<String,String> map);
}
