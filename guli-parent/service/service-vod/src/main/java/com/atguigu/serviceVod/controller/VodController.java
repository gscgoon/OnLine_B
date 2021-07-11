package com.atguigu.serviceVod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonUtils.R;
import com.atguigu.serviceVod.service.VodService;
import com.atguigu.serviceVod.util.AliyunVodSdkUtils;
import com.atguigu.serviceVod.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Super
 * @date 2020-04-06 15:17
 */
@Api(description = "阿里云视频点播")
@RestController
@RequestMapping("/admin/serviceVod/video")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 视频上传
     * @param file
     * @return R
     * @throws Exception
     */
    @ApiOperation("视频上传")
    @PostMapping("/uploadVideo")
    public R uploadVideo(@ApiParam(name = "file",value = "章节视频",required = true)
                          @RequestParam("file") MultipartFile file
                         )throws Exception{
        //返回视频的id
        String videoId = vodService.uploadVideoFile(file);
        return R.ok().data("videoId",videoId);
    }


    /**
     * 删除云端的视频
     * @param videoId
     * @return R
     */
    @ApiOperation("删除云端的视频")
    @DeleteMapping("/deleteAliYunVideo/{videoId}")
    public R deleteAliYunVideo(@ApiParam(name = "videoId",value = "阿里云视频",required = true)
                               @PathVariable String videoId
                               ){
        vodService.deleteAliyunVideoById(videoId);
        return R.ok().message("云端视频删除成功！");
    }

    /**
     * 删除云端所有的视频
     * @param videoIdList
     * @return R
     */
    @ApiOperation("删除云端所有的视频")
    @DeleteMapping("/deleteAllAliYunVideo")
    public R deleteAllAliYunVideo(@ApiParam(name = "videoIdList",value = "多个视频",required = true)
                               @RequestParam("videoIdList") List<String> videoIdList
                                ){
        vodService.removeAllAliyunVideoById(videoIdList);
        return R.ok().message("云端该课程所有视频删除成功！");
    }

    /**
     * 视频播放
     * @param videoId
     * @return R
     * @throws Exception
     */
    @ApiOperation("视频播放")
    @GetMapping("/getPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {

        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client = AliyunVodSdkUtils.initVodClient(accessKeyId, accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }

}
