package com.nicklaus.servicestatistics.controller;


import com.nicklaus.commonutils.Result;
import com.nicklaus.servicestatistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/serviceStatistics/statisticsDaily")
@Api(tags = "网站数据统计分析管理")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    StatisticsDailyService statisticsDailyService;

    @PostMapping("createStatistics/{day}")
    @ApiOperation("根据日期增加一条统计记录")
    public Result createStatisticsByDay(@PathVariable(name = "day") String day){
        statisticsDailyService.createStatisticsByDay(day);
        return Result.ok();
    }

    @GetMapping("getChartData/{type}/{begin}/{end}")
    @ApiOperation("获取统计数据图表的数据")
    public Result getChartData(@PathVariable(name = "type") String type,
                               @PathVariable(name = "begin") String begin,
                               @PathVariable(name = "end") String end){
        Map<String, Object> map = statisticsDailyService.getChartData(type, begin, end);
        return Result.ok().data(map);
    }
}

