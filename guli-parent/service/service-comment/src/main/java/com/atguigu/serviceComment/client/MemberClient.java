package com.atguigu.serviceComment.client;

import com.atguigu.commonUtils.vo.UserCenterMemberCommentVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Super
 * @date 2020-04-21 14:31
 */
@Component
@FeignClient("service-user-center")
public interface MemberClient
{
    /**
     * 根据id获取用户信息
     * @param id
     * @return UserCenterMemberCommentVo
     */
    @GetMapping("/admin/serviceUserCenter/user-center-member/getMemberById/{id}")
    public UserCenterMemberCommentVo getLoginInfo(@PathVariable("id") String id);
}
