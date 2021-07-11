package com.atguigu.serviceStatistics.service;

import com.atguigu.serviceStatistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-13
 */
public interface DailyService extends IService<Daily> {

    /**
     * 根据日期统计数据
     * @param day
     * @return
     */
    boolean statisticsData(String day);

    /**
     * 图表显示统计数据
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String,Object> showChart(String type, String begin, String end);
}
