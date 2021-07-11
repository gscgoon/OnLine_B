package com.atguigu.serviceEdu.service;

import com.atguigu.serviceEdu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-02
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除小节
     * @param id
     */
    void deleteVideoByCourseId(String id);
}
