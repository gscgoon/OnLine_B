package com.atguigu.serviceOrder.service.impl;

import com.atguigu.commonUtils.vo.CourseDetailWebVoOrder;
import com.atguigu.commonUtils.vo.UserCenterMemberOrderVo;
import com.atguigu.serviceOrder.client.EduCourseClient;
import com.atguigu.serviceOrder.client.UserCenterMemberClient;
import com.atguigu.serviceOrder.entity.TOrder;
import com.atguigu.serviceOrder.mapper.TOrderMapper;
import com.atguigu.serviceOrder.service.TOrderService;
import com.atguigu.serviceOrder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-11
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private UserCenterMemberClient userCenterMemberClient;

    @Autowired
    private EduCourseClient eduCourseClient;



    /**
     * 根据课程id，生成订单号，并返回订单号
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public String addOrderByCourseId(String courseId, String memberId) {

        //1.远程调用edu,查询课程的信息
        CourseDetailWebVoOrder courseDetailWebVoOrder = eduCourseClient.getCourseInfoOrder(courseId);
        //2.远程调用userCenterMember，查用户信息
        UserCenterMemberOrderVo userCenterMemberOrderVo = userCenterMemberClient.getMemberInfoOrder(memberId);

        //3.生成订单
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDetailWebVoOrder.getTitle());
        order.setCourseCover(courseDetailWebVoOrder.getCover());
        order.setTeacherName(courseDetailWebVoOrder.getTeacherName());
        order.setTotalFee(courseDetailWebVoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userCenterMemberOrderVo.getMobile());
        order.setNickname(userCenterMemberOrderVo.getNickName());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
