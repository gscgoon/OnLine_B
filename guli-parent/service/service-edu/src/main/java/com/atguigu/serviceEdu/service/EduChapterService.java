package com.atguigu.serviceEdu.service;

import com.atguigu.serviceEdu.entity.EduChapter;
import com.atguigu.serviceEdu.entity.chapter.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-02
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 获取所有章节
     * @return
     * @param courseId
     */
    List<Chapter> getAllChapter(String courseId);

    /**
     * 删除章节
     * @param id
     * @return
     */
    boolean deleteChapterById(String id);

    /**
     * 根据课程id，删除该课程的所有章节
     * @param id
     */
    void deleteChapterByCourseId(String id);


}
