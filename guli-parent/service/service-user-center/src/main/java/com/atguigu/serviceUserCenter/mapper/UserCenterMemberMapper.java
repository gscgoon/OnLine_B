package com.atguigu.serviceUserCenter.mapper;

import com.atguigu.serviceUserCenter.entity.UserCenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author super
 * @since 2020-04-08
 */
public interface UserCenterMemberMapper extends BaseMapper<UserCenterMember> {

    /**
     * 某天的注册人数
     * @param day
     * @return
     */
    Integer countRegister(String day);
}
