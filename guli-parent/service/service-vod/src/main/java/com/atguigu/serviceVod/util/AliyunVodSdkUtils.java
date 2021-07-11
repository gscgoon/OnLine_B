package com.atguigu.serviceVod.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

//阿里云vod工具类
/**
 * @author Super
 * @date 2020-04-06 15:12
 */
public class AliyunVodSdkUtils {


    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {

        // 点播服务接入区域
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
