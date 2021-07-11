package com.atguigu.serviceEdu.client;

import com.atguigu.commonUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Super
 * @date 2020-04-07 7:39
 */
@FeignClient(value = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

    /**
     * 调用vod服务，删除云端视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/admin/serviceVod/video/deleteAliyunVideo/{videoId}")
    //这里的@PathVariable注解中必须有参数值，不然会报错：Caused by: java.lang.IllegalStateException: PathVariable annotation was empty on param 0.
    public R remove(@PathVariable("videoId") String videoId);

    /**
     * 删除该课程所有视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/admin/serviceVod/video/deleteAllAliyunVideo")
    public R removeAll(@RequestParam("videoIdList") List<String> videoIdList);

}
