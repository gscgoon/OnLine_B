package com.atguigu.serviceStatistics.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.serviceStatistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-13
 */
@Api(description = "统计模块")
@RestController
@RequestMapping("/admin/serviceStatistics/daily")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class DailyController {

    @Autowired
    private DailyService dailyService;


    @ApiOperation("根据日期统计数据")
    @GetMapping("/statisticsData/{day}")
    public R statisticsData(@ApiParam(name = "day",value = "统计日期",required = true)
                            @PathVariable String day
                            ){
        boolean flag = dailyService.statisticsData(day);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("图表统计")
    @GetMapping("/showChart/{type}/{begin}/{end}")
    public R showChart(@ApiParam(name = "type",value = "统计类型",required = true)
                        @PathVariable String type,
                        @ApiParam(name = "begin",value = "开始时间",required = true)
                        @PathVariable String begin,
                        @ApiParam(name = "end",value = "结束时间",required = true)
                        @PathVariable String end
                       ){
        //将需要的横轴数据和纵轴数据封装到map中
        Map<String,Object> map = dailyService.showChart(type,begin,end);
        return R.ok().data(map);
    }
}

