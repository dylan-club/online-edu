package com.nicklaus.servicestatistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nicklaus.servicestatistics.client.UserClient;
import com.nicklaus.servicestatistics.entity.StatisticsDaily;
import com.nicklaus.servicestatistics.mapper.StatisticsDailyMapper;
import com.nicklaus.servicestatistics.service.StatisticsDailyService;
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
 * @author nicklaus
 * @since 2020-09-26
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UserClient userClient;

    @Override
    public void createStatisticsByDay(String day) {
        //删除已有记录
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //更新当天记录
        int count = (int)userClient.countRegistration(day).getData().get("countRegistration");

        StatisticsDaily daily = new StatisticsDaily();
        daily.setDateCalculated(day);
        daily.setRegisterNum(count);
        //设置随机值
        daily.setCourseNum(RandomUtils.nextInt(100,200));
        daily.setLoginNum(RandomUtils.nextInt(100,200));
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));

        //添加记录到数据库
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String type, String begin, String end) {
        //获取指定时间段的统计数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> statisticsDailyList = baseMapper.selectList(wrapper);

        //将查询的数据封装成两个list
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();

        //遍历封装
        for (int i = 0; i < statisticsDailyList.size(); i++) {
            StatisticsDaily statisticsDaily = statisticsDailyList.get(i);
            //封装日期
            dateList.add(statisticsDaily.getDateCalculated());
            //判断数据字段并添加数据
            switch (type){
                case "register_num":
                    dataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        //将数据存储在map中
        Map<String, Object> map = new HashMap<>();

        map.put("dateList", dateList);
        map.put("dataList", dataList);

        return map;
    }
}
