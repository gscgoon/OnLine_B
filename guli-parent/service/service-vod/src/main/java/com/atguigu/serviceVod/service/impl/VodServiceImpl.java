package com.atguigu.serviceVod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceVod.service.VodService;
import com.atguigu.serviceVod.util.AliyunVodSdkUtils;
import com.atguigu.serviceVod.util.ConstantPropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Super
 * @date 2020-04-06 17:38
 */
@Service
public class VodServiceImpl implements VodService {

    /**
     * 上传章节视频
     * @param file
     * @return
     */
    @Override
    public String uploadVideoFile(MultipartFile file) {

        try {
            //获取视频文件流
            InputStream inputStream = file.getInputStream();
            //获取源视频文件的名称
            String originalFilename = file.getOriginalFilename();
            //上传到阿里云oss的视频文件名
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    /**
     * 删除云端视频
     * @param videoId
     */
    @Override
    public void deleteAliyunVideoById(String videoId) {

        try {
            DefaultAcsClient client = AliyunVodSdkUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除云端视频失败！");
        }



    }

    /**
     * 删除所有视频
     * @param videoIdList
     */
    @Override
    public void removeAllAliyunVideoById(List videoIdList) {

        try {
            DefaultAcsClient allClient = AliyunVodSdkUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();
            //将视频id的集合，解析为（1，2，3）这种格式
            String idStr = StringUtils.join(videoIdList.toArray(),",");

            request.setVideoIds(idStr);
            DeleteVideoResponse response = allClient.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除云端视频失败！");
        }


    }
}
