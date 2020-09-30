package com.nicklaus.serviceedu.client;

import com.nicklaus.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduVod/aliyunVod/deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduVod/aliyunVod/deleteVideoList")
    public Result deleteVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
