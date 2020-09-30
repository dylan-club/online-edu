package com.nicklaus.servicemsm.controller;

import com.nicklaus.commonutils.Result;
import com.nicklaus.servicemsm.service.MsmService;
import com.nicklaus.servicemsm.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/servicemsm")
//@CrossOrigin
@Api(tags = "短信服务管理")
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("sendMessage/{phoneNumber}")
    @ApiOperation("发送短信")
    public Result sendMessage(@PathVariable(name = "phoneNumber") String phoneNumber){

        //判断redis中是否有值
        String code = redisTemplate.opsForValue().get(phoneNumber);
        //有值直接返回
        if (!StringUtils.isEmpty(code)){
            return Result.ok();
        }
        //生成4位验证码
        code = RandomUtil.getFourBitRandom();  // 验证码:2258

        Map<String, Object> params = new HashMap<>();
        params.put("code",code);
        //发送短信
        boolean flag = msmService.sendAliyunMsg(params, phoneNumber);

        if (flag){
            redisTemplate.opsForValue().set(phoneNumber,code,5, TimeUnit.MINUTES);
            return Result.ok();
        }else{
            return Result.error().message("短信发送失败");
        }
    }
}
