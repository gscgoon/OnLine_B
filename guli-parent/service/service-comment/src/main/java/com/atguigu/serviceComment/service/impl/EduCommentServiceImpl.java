package com.atguigu.serviceComment.service.impl;

import com.atguigu.commonUtils.vo.UcenterMemberCommentVo;
import com.atguigu.serviceComment.client.MemberClient;
import com.atguigu.serviceComment.entity.EduComment;
import com.atguigu.serviceComment.mapper.EduCommentMapper;
import com.atguigu.serviceComment.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-20
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {


    @Autowired
    private MemberClient memberClient;

    /**
     * 根据课程id查该课程的所有评论
     * @param pageComment
     * @param courseId
     */
    @Override
    public Map getComments(Page<EduComment> pageComment, String courseId)
    {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        baseMapper.selectPage(pageComment,wrapper);
        List<EduComment> records = pageComment.getRecords();
        long total = pageComment.getTotal();
        long current = pageComment.getCurrent();
        long size = pageComment.getSize();
        long pages = pageComment.getPages();
        boolean hasNext = pageComment.hasNext();
        boolean hasPrevious = pageComment.hasPrevious();

        HashMap<String, Object> map = new HashMap<>();

        map.put("records", records);
        map.put("total", total);
        map.put("current", current);
        map.put("size", size);
        map.put("pages", pages);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    /**
     * 添加评论
     * @param eduComment
     * @param memberId
     */
    @Override
    public boolean addComment(EduComment eduComment, String memberId)
    {
        UcenterMemberCommentVo memberCommentVo = memberClient.getLoginInfo(memberId);
//        System.out.println("memberCommetVo>>>>>>>>>>>>>>>>>>>"+memberCommentVo);
        eduComment.setMemberId(memberId);
        eduComment.setAvatar(memberCommentVo.getAvatar());
        eduComment.setNickname(memberCommentVo.getNickname());
        int count = baseMapper.insert(eduComment);
        return count > 0;
    }
}
