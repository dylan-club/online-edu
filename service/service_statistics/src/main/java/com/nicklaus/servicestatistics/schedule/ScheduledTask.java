package com.nicklaus.servicestatistics.schedule;

import com.nicklaus.servicestatistics.service.StatisticsDailyService;
import com.nicklaus.servicestatistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService  statisticsDailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void countRegistrationTask(){
        //获取前一天的数据
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.createStatisticsByDay(day);
    }
}
