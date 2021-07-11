package com.atguigu.serviceVod.util;

//读取配置文件的常量类

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Super
 * @date 2020-04-06 15:06
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.vod.file.keyd}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {

        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
