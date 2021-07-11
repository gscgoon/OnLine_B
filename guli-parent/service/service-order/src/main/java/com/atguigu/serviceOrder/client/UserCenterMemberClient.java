package com.atguigu.serviceOrder.client;

import com.atguigu.commonUtils.vo.UserCenterMemberOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Super
 * @date 2020-04-12 8:50
 */
@FeignClient("service-user-center")
@Component
public interface UserCenterMemberClient {

    /**
     * 远程调用ucenter,查订单用户信息
     * @param id
     * @return
     */
    @GetMapping("/web/serviceUserCenter/member/getMemberOrder/{id}")
    public UserCenterMemberOrderVo getMemberInfoOrder(@PathVariable("id") String id);
}
