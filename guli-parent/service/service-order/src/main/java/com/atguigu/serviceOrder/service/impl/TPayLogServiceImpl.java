package com.atguigu.serviceOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceOrder.service.TOrderService;
import com.atguigu.serviceOrder.service.TPayLogService;
import com.atguigu.serviceOrder.utils.HttpClient;
import com.atguigu.serviceOrder.entity.TOrder;
import com.atguigu.serviceOrder.entity.TPayLog;
import com.atguigu.serviceOrder.mapper.TPayLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-11
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderService tOrderService;

    /**
     * 生成二维码
     * @param orderNo
     */
    @Override
    public Map payOrder(String orderNo) {

        try {
            //1.根据订单号查订单信息
            QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            TOrder order = tOrderService.getOne(wrapper);

            //设置生成二维码的参数，放到map中
            Map map = new HashMap();
            //2.设置支付参数
            map.put("appid", "wx74862e0dfcf69954");//微信id
            map.put("mch_id", "1558950191");//商户号
            map.put("nonce_str", WXPayUtil.generateNonceStr());//随机数，每个二维码不同
            map.put("body", order.getCourseTitle());//二维码生成的名字
            map.put("out_trade_no", orderNo);//订单号，交易号
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");//课程费用
            map.put("spbill_create_ip", "127.0.0.1");//支付IP
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");//支付成功后回调地址
            map.put("trade_type", "NATIVE");//根据价格生成二维码


            //3.HTTPClient来根据URL访问第三方接口并且传递参数，微信支付的固定地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置xml参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));//商户key
            client.setHttps(true);
            //发送请求
            client.post();

            //4.返回第三方的响应结果（xml格式）
            String xml = client.getContent();

            //将xml格式数据转为map格式返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //5.封装返回结果集
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());//要跳转的课程id
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));//二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));//二维码地址

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"二维码生成失败");
        }
    }

    /**
     * 查询订单状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> checkOrderStatus(String orderNo) {

        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转为map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //5、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            //有异常就返回空
            return null;
        }
    }

    /**
     * 修改订单状态，并向支付表中添加支付数据
     * @param map
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {

        //1.修改订单状态
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = tOrderService.getOne(wrapper);

        if(order.getStatus().intValue() == 1) {
            //状态为1直接返回
            return;
        }
        //设置为已支付
        order.setStatus(1);
        tOrderService.updateById(order);

        //2.支付表添加数据
        TPayLog tPayLog =new TPayLog();
        //支付订单号
        tPayLog.setOrderNo(order.getOrderNo());
        tPayLog.setPayTime(new Date());
        //支付类型
        tPayLog.setPayType(1);
        //总金额(分)
        tPayLog.setTotalFee(order.getTotalFee());
        //支付状态
        tPayLog.setTradeState(map.get("trade_state"));
        //支付流水号
        tPayLog.setTransactionId(map.get("transaction_id"));
        //将其他属性存储为字符串格式
        tPayLog.setAttr(JSONObject.toJSONString(map));
        //插入到支付日志表
        baseMapper.insert(tPayLog);
    }

}
