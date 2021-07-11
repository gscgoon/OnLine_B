package com.atguigu.serviceStatistics.service.impl;

import com.atguigu.commonUtils.R;
import com.atguigu.serviceStatistics.client.UserCentClient;
import com.atguigu.serviceStatistics.entity.Daily;
import com.atguigu.serviceStatistics.mapper.DailyMapper;
import com.atguigu.serviceStatistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-13
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {


    @Autowired
    private UserCentClient userCentClient;

    /**
     * 根据日期统计数据
     * @param day
     * @return
     */
    @Override
    public boolean statisticsData(String day) {
        //在每次生成统计数据时，先清空要生成统计当日的数据库的数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        //1.统计登录人数
        Integer loginNum = RandomUtils.nextInt(100,200);
        //2.统计注册人数
        R r = userCentClient.countRegister(day);
        Integer regNum = (Integer) r.getData().get("count");
        //3.统计每日视频播放数量
        Integer videoNum = RandomUtils.nextInt(100,200);
        //4.统计每日新增课程数
        Integer courseNum = RandomUtils.nextInt(100,200);
        Daily daily = new Daily();
        daily.setRegisterNum(regNum);
        daily.setLoginNum(loginNum);
        daily.setCourseNum(courseNum);
        daily.setVideoViewNum(videoNum);
        daily.setDateCalculated(day);
        //插入数据到统计表
        int insert = baseMapper.insert(daily);
        return insert > 0;
    }

    /**
     * 图表显示统计数据
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> showChart(String type, String begin, String end) {

        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        //查询对应的统计类型
        wrapper.select(type,"date_calculated");
        //日期的区间
        wrapper.between("date_calculated",begin,end);
        List<Daily> dailyList = baseMapper.selectList(wrapper);

        //前台需要的横轴和纵轴的数据类型为[1,2,3]/['','',]，需要将得到的数据集合，循环遍历分别封装到两个集合中
        //纵轴数量集合
        List<Integer> yNumList = new ArrayList<>();
        //横轴日期集合
        List<String> xDateList = new ArrayList<>();
        for (int i = 0; i <dailyList.size() ; i++) {
            Daily daily = dailyList.get(i);
            //先封装纵轴数量集合,需要判断统计类型
            switch (type){
                case "login_num":
                    yNumList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    yNumList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    yNumList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    yNumList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
            //封装日期集合
            xDateList.add(daily.getDateCalculated());
        }
        //定义map集合，用来存储统计的纵轴和横轴数据
        Map<String,Object> map = new HashMap<>();
        map.put("yNumList",yNumList);
        map.put("xDateList",xDateList);
        return map;
    }
}
