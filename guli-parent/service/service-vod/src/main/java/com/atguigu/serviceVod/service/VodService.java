package com.atguigu.serviceVod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Super
 * @date 2020-04-06 17:38
 */
public interface VodService {
    /**
     * 上传章节视频
     * @param file
     * @return
     */
    String uploadVideoFile(MultipartFile file);

    /**
     * 删除云端的视频
     * @param videoId
     */
    void deleteAliyunVideoById(String videoId);

    /**
     * 删除该课程的云端所有视频
     * @param videoIdList
     */
    void removeAllAliyunVideoById(List videoIdList);
}
