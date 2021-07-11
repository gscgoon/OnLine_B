package com.atguigu.serviceUserCenter.service.impl;

import com.atguigu.commonUtils.JwtUtils;
import com.atguigu.commonUtils.MD5;
import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceUserCenter.entity.UserCenterMember;
import com.atguigu.serviceUserCenter.entity.vo.LoginVo;
import com.atguigu.serviceUserCenter.entity.vo.RegisterVo;
import com.atguigu.serviceUserCenter.mapper.UserCenterMemberMapper;
import com.atguigu.serviceUserCenter.service.UserCenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-08
 */
@Service
public class UserCenterMemberServiceImpl extends ServiceImpl<UserCenterMemberMapper, UserCenterMember> implements UserCenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 会员注册
     * @param registerVo
     * @return
     */
    @Override
    public boolean registMember(RegisterVo registerVo) {

        String nickName = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数是否为空
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"会员注册失败，注册信息为空");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"验证码有误");
        }

        //1.校验手机号是否存在
        QueryWrapper<UserCenterMember> userCenterMemberWrapper = new QueryWrapper<>();
        userCenterMemberWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(userCenterMemberWrapper);
        if(count > 0){
            throw new GuliException(20001,"此手机号已被注册");
        }

        UserCenterMember userCenterMember = new UserCenterMember();
        userCenterMember.setMobile(mobile);
        userCenterMember.setNickName(nickName);
        //密码MD5加密
        userCenterMember.setPassword(MD5.encrypt(password));
        userCenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        Integer memberCount = baseMapper.insert(userCenterMember);
        return memberCount > 0;
    }

    /**
     * 会员登录
     * @param loginVo
     * @return token
     */
    @Override
    public String login(LoginVo loginVo) {
        //获取登录信息
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验会员登录信息
        if(StringUtils.isEmpty(mobile)|| StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录信息有误");
        }

        //查会员
        QueryWrapper<UserCenterMember> userCenterMemberWrapper = new QueryWrapper<>();
        userCenterMemberWrapper.eq("mobile",mobile);
        UserCenterMember userCenterMember = baseMapper.selectOne(userCenterMemberWrapper);
        if(userCenterMember == null ){
            throw new GuliException(20001,"error");
        }
        //校验会员的密码
        if(!MD5.encrypt(password).equals(userCenterMember.getPassword())){
            throw new GuliException(20001,"error");
        }
        //校验会员是否禁用
        if(userCenterMember.getIsDisabled()){
            throw new GuliException(20001,"error");
        }
        //生成token
        String token = JwtUtils.getJwtToken(userCenterMember.getId(),userCenterMember.getNickName());
        return token;
    }

    /**
     * 根据token信息获取登录信息
     * @param memberId
     * @return userCenterMember
     */
    @Override
    public UserCenterMember getLoginInfo(String memberId) {
        UserCenterMember userCenterMember = baseMapper.selectById(memberId);
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(userCenterMember,loginVo);
        return userCenterMember;
    }

    /**
     * 根据微信id查member
     * @param openid
     * @return userCenterMember
     */
    @Override
    public UserCenterMember getByOpenid(String openid) {

        QueryWrapper<UserCenterMember> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("open_id",openid);
        UserCenterMember userCenterMember = baseMapper.selectOne(memberQueryWrapper);
        return userCenterMember;
    }

    /**
     * 某天的注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }
}
