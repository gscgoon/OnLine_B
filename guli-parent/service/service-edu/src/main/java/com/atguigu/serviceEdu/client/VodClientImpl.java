package com.atguigu.serviceEdu.client;

import com.atguigu.commonUtils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Super
 * @date 2020-04-08 0:13
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R remove(String videoId) {
        return R.error().message("视频删除失败");
    }

    @Override
    public R removeAll(List<String> videoIdList) {
        return R.error().message("所有视频删除失败");
    }
}
