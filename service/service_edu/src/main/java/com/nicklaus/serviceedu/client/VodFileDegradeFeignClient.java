package com.nicklaus.serviceedu.client;

import com.nicklaus.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result deleteVideo(String videoId) {
        return Result.error().message("删除视频time out");
    }

    @Override
    public Result deleteVideoList(List<String> videoIdList) {
        return Result.error().message("批量删除视频time out");
    }
}
