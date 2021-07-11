package com.atguigu.serviceUserCenter.controller.api;

import com.atguigu.commonUtils.JwtUtils;
import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceUserCenter.entity.UserCenterMember;
import com.atguigu.serviceUserCenter.service.UserCenterMemberService;
import com.atguigu.serviceUserCenter.utils.ConstantPropertiesUtil;
import com.atguigu.serviceUserCenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author Super
 * @date 2020-04-09 14:09
 */
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
@Controller//注意这里没有配置 @RestController，这里不需要返回对象，只进行url地址请求
@RequestMapping("/api/userCenter/wx")
public class WxApiController {

    @Autowired
    private UserCenterMemberService userCenterMemberService;

    /**
     * 微信扫描登录
     * @param session
     * @return
     */
    @GetMapping("/login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl,%s：表示占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        //获取业务服务器重定向地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            //url编码
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        //为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        String state = "imhelen";
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }


    /**
     * 回调方法
     * @param code
     * @param state
     * @param session
     * @return
     */
    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session) {

        //1.得到授权临时票据code
//        System.out.println(code);
//        System.out.println(state);

//        //从redis中将state获取出来，和当前传入的state作比较
//        //如果一致则放行，如果不一致则抛出异常：非法访问
//
//        //2.带着code 请求微信的固定地址 （向认证服务器发送请求）换取access_token，openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        String result = null;
        try {
            //发送请求获取access_token
            result = HttpClientUtils.get(accessTokenUrl);
//            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }

        //解析json字符串
        Gson gson = new Gson();
        //转换数据格式为map格式，方便取值access_token，openid
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //查询数据库当前用用户是否曾经使用过微信登录
        UserCenterMember userCenterMember = userCenterMemberService.getByOpenid(openid);
        if(userCenterMember == null){
            System.out.println("新用户注册");

            //3.拼接access_token ,openid 访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new GuliException(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String)mapUserInfo.get("nickname");
            String headImgUrl = (String)mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            userCenterMember = new UserCenterMember();
            userCenterMember.setNickName(nickname);
            userCenterMember.setOpenId(openid);
            userCenterMember.setAvatar(headImgUrl);
            userCenterMemberService.save(userCenterMember);
        }

        //需要生成token拼接到路径中，前端页面中得到token，调用后台接口解析token得到用户信息
        String token = JwtUtils.getJwtToken(userCenterMember.getId(),userCenterMember.getNickName());

        return "redirect:http://localhost:3000?token="+token;
    }
}
