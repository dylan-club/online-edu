package com.nicklaus.serviceorder.controller;


import com.nicklaus.commonutils.Result;
import com.nicklaus.serviceorder.service.TPayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-24
 */
@RestController
@RequestMapping("/serviceOrder/payLog")
//@CrossOrigin
@Api(tags = "订单日志管理")
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    @GetMapping("createNative/{orderNo}")
    @ApiOperation("生成微信支付二维码")
    public Result createNative(@PathVariable String orderNo){
        Map<String, Object> map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    @GetMapping("queryPayStatus/{orderNo}")
    @ApiOperation("查询支付状态")
    public Result queryPayStatus(@PathVariable String orderNo){
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null){
            //支付失败
            return Result.error().message("订单支付出错");
        }

        if(map.get("trade_state").equals("SUCCESS")){
            //支付成功
            //修改订单状态并生成支付日志
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(25000).message("支付中");
    }
}

