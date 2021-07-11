package com.atguigu.serviceEdu.service.impl;

import com.atguigu.serviceEdu.client.VodClient;
import com.atguigu.serviceEdu.entity.EduVideo;
import com.atguigu.serviceEdu.mapper.EduVideoMapper;
import com.atguigu.serviceEdu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    /**
     * 注入vodClient
     */
    @Autowired
    private VodClient vodClient;

    /**
     * 根据课程id删除改课程的所有小节
     * @param id
     * @return
     */
    @Override
    public void deleteVideoByCourseId(String id) {

        //删除小节时，将云端视频删除
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",id);
        //查出所有视频
        videoQueryWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(videoQueryWrapper);

        //得到视频id集合
        List<String> videoSourceIdList =  new ArrayList<>();
        //遍历集合
        for (int i = 0; i <eduVideoList.size() ; i++) {
            EduVideo video = eduVideoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            //判断视频id是否为空
            if(!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }

        //判断视频id集合是否为空
        if(videoSourceIdList.size()>0){
            vodClient.removeAll(videoSourceIdList);
        }
        //然后删除小节本身
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        baseMapper.delete(queryWrapper);
    }
}
