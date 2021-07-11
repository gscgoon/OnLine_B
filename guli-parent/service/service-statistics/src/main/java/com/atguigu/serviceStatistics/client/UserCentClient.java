package com.atguigu.serviceStatistics.client;

import com.atguigu.commonUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Super
 * @date 2020-04-13 8:38
 */
@Component
@FeignClient("service-user-center")
public interface UserCentClient {

    /**
     * 统计注册人数
     * @param day
     * @return
     */
    @GetMapping("/admin/serviceUserCenter/user-center-member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
