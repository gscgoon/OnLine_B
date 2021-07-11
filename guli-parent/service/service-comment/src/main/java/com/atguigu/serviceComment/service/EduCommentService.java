package com.atguigu.serviceComment.service;

import com.atguigu.serviceComment.entity.EduComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-20
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 根据课程id查该课程的评论
     * @param pageComment
     * @param courseId
     */
    Map getComments(Page<EduComment> pageComment, String courseId);

    /**
     * 添加评论
     * @param eduComment
     * @param memberId
     */
    boolean addComment(EduComment eduComment, String memberId);
}
