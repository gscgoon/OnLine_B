package com.atguigu.serviceStatistics.schedule;

import com.atguigu.serviceStatistics.service.DailyService;
import com.atguigu.serviceStatistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Super
 * @date 2020-04-13 19:01
 */
@Component
public class ScheduleTest {

    @Autowired
    private DailyService dailyService;

//    @Scheduled(cron = "0 6 0 19 * ? ")
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void test1(){//这个方法不能使用test()方法，否则没反应
//        System.out.println("测试定时任务");
//    }

    //每日凌晨统计前一天的数据
    @Scheduled(cron = "0 0 0 * * ? ")
    public void excuteCount(){
        //执行统计方法，今天统计前一天的数据
        System.out.println("***********执行了定时任务");
        dailyService.statisticsData(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
