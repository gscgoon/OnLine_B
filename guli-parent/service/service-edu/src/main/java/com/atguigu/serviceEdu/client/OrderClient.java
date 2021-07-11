package com.atguigu.serviceEdu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Super
 * @date 2020-04-12 23:47
 */
@Component
@FeignClient("service-order")
public interface OrderClient {

    /**
     * 课程是否被购买
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("/web/serviceOrder/t-order/courseIsBuy/{courseId}/{memberId}")
    public boolean courseIsBuy(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
