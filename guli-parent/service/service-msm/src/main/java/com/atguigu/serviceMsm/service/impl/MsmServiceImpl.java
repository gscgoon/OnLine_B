package com.atguigu.serviceMsm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.serviceMsm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author Super
 * @date 2020-04-08 22:57
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean sendNumber(String phoneNumber, HashMap<String, Object> param) {

        //判断手机号是否为空
        if(StringUtils.isEmpty(phoneNumber))
        {
            return false;
        }

        //设置阿里云的keyid,keysecret
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4Fi5zQUW8PUmTnQxALXs", "SywSWbXd2EFPYMzM3xyDbHEHRgZetd");
        IAcsClient client = new DefaultAcsClient(profile);

        //请求
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");


        request.putQueryParameter("PhoneNumbers", phoneNumber);
        //阿里云短信服务的签名管理的
        request.putQueryParameter("SignName", "咖喱在线教育平台");
        //阿里云短信服务的模板管理的模版CODE
        request.putQueryParameter("TemplateCode", "SMS_187590821");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
