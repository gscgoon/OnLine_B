package com.atguigu.serviceOrder.service;

import com.atguigu.serviceOrder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-11
 */
public interface TOrderService extends IService<TOrder> {

    /**
     * 生成订单并返回订单号
     * @param courseId
     * @param memberId
     * @return
     */
    String addOrderByCourseId(String courseId, String memberId);
}
