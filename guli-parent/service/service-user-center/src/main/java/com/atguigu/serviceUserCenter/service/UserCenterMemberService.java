package com.atguigu.serviceUserCenter.service;

import com.atguigu.serviceUserCenter.entity.UserCenterMember;
import com.atguigu.serviceUserCenter.entity.vo.LoginVo;
import com.atguigu.serviceUserCenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-08
 */
public interface UserCenterMemberService extends IService<UserCenterMember> {

    /**
     * 会员注册
     * @param registerVo
     * @return
     */
    boolean registMember(RegisterVo registerVo);

    /**
     * 会员登录
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 根据token信息获取登录信息
     * @param memberId
     * @return
     */
    UserCenterMember getLoginInfo(String memberId);

    /**
     * 根据微信id查member
     * @param openid
     * @return
     */
    UserCenterMember getByOpenid(String openid);

    /**
     * 某天的注册人数
     * @param day
     * @return
     */
    Integer countRegister(String day);
}
