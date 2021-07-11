package com.atguigu.serviceEdu.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.serviceBase.handler.GuliException;
import com.atguigu.serviceEdu.client.VodClient;
import com.atguigu.serviceEdu.entity.EduVideo;
import com.atguigu.serviceEdu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-02
 */
@Api(description = "章节中添加小节")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
@RestController
@RequestMapping("/admin/serviceEdu/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @ApiOperation("添加小节")
    @PostMapping("/add")
    public R addVideo(@ApiParam(name = "video",value = "小节对象",required = true)
                      @RequestBody EduVideo eduVideo
                      ){
        boolean flag = eduVideoService.save(eduVideo);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


    /**
     * 删除小节
     * @param id
     * @return
     */
    @ApiOperation("删除小节")
    @DeleteMapping("/delete/{id}")
    public R delVideo(@ApiParam(name = "id",value = "小节id",required = true)
                      @PathVariable String id
                      ){
        //删除小节前，先删除云端视频
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
//            vodClient.remove(videoSourceId);
            //测试熔断器
            R r = vodClient.remove(videoSourceId);
            if(r.getCode() == 20001){
                throw new GuliException(20001,"熔断器起作用");
            }
        }
        eduVideoService.removeById(id);
        return R.ok();
    }

    /**
     * 修改小节前查询小节用于回显
     * @param id
     * @return
     */
    @ApiOperation("根据id查询小节")
    @GetMapping("/getVideoById/{id}")
    public R getVideo(@ApiParam(name = "id",value = "小节id",required = true)
                      @PathVariable String id
                      ){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("eduVido",eduVideo);
    }

    @ApiOperation("修改小节")
    @PostMapping("/update")
    public  R updateVideo(@ApiParam(name = "video",value = "小节对象",required = true)
                         @RequestBody EduVideo eduVideo
                         ){
        boolean flag = eduVideoService.updateById(eduVideo);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

