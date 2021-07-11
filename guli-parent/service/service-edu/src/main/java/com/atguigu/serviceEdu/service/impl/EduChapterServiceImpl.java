package com.atguigu.serviceEdu.service.impl;

import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceEdu.entity.EduChapter;
import com.atguigu.serviceEdu.entity.EduVideo;
import com.atguigu.serviceEdu.entity.chapter.Chapter;
import com.atguigu.serviceEdu.entity.chapter.ChapterVideo;
import com.atguigu.serviceEdu.mapper.EduChapterMapper;
import com.atguigu.serviceEdu.service.EduChapterService;
import com.atguigu.serviceEdu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 查章节和小节
     * @return
     * @param courseId
     */
    @Override
    public List<Chapter> getAllChapter(String courseId) {
        //1.查所有章节，建立在某个课程之上，需要课程id
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterQueryWrapper);

        //2.查所有小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);

        ArrayList<Chapter> chapterFinalList = new ArrayList<>();
        //3.封装章节和小节
        for (int i = 0; i < eduChapterList.size() ; i++) {
            //3.1封装章节
            //遍历章节集合，将属性拷贝到chapter,并将chapter添加到最终的集合中
            EduChapter eduChapter = eduChapterList.get(i);
            Chapter chapter = new Chapter();
            //属性拷贝工具
            BeanUtils.copyProperties(eduChapter,chapter);
            chapterFinalList.add(chapter);

            //3.2封装小节
            //定义存放小节的集合
            ArrayList<ChapterVideo> videoList = new ArrayList<>();
            //遍历拷贝属性
            for (int j = 0; j <eduVideoList.size() ; j++) {

                EduVideo eduVideo = eduVideoList.get(j);
                //判断小节的章节chapter_id是否等于章节的id
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    ChapterVideo chapterVideo = new ChapterVideo();
                    BeanUtils.copyProperties(eduVideo,chapterVideo);
                    videoList.add(chapterVideo);
                }
            }
            //3.3设置chapter的children属性
            chapter.setChildren(videoList);
        }
        //4.返回最终的封装结果
        return chapterFinalList;
    }

    /**
     * 删除章节
     * @param id
     * @return
     */
    @Override
    public boolean deleteChapterById(String id) {
        //删除章节前，查询章节下是否有小节存在，如存在就给出信息
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",id);
        int count = eduVideoService.count(videoQueryWrapper);
        if(count > 0){
            throw new  GuliException(20001,"该章节下有视频，不能删除！");
        }else {
            int result = baseMapper.deleteById(id);
            return result > 0;
        }
    }

    /**
     * 根据课程id，删除该科程的所有章节
     * @param id
     */
    @Override
    public void deleteChapterByCourseId(String id) {

        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        baseMapper.delete(queryWrapper);
    }



}
