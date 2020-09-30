package com.nicklaus.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.baomidou.mybatisplus.extension.api.R;
import com.nicklaus.commonutils.Result;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.vod.service.VodService;
import com.nicklaus.vod.util.AliyunVodSDKUtils;
import com.nicklaus.vod.util.ConstantPropertiesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "阿里云视频点播服务")
@RestController
@RequestMapping("/eduVod/aliyunVod")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    @ApiOperation("上传小节视频至阿里云")
    public Result uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return Result.ok().data("videoId",videoId);
    }

    @DeleteMapping("deleteVideo/{videoId}")
    @ApiOperation("删除阿里云的小节视频")
    public Result deleteVideo(@PathVariable String videoId){
        vodService.deleteVideoByVideoId(videoId);
        return Result.ok();
    }

    @DeleteMapping("deleteVideoList")
    @ApiOperation("批量删除阿里云小节视频")
    public Result deleteVideoList(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteVideoList(videoIdList);
        return Result.ok();
    }

    @GetMapping("getVideoPlayAuth/{videoSourceId}")
    @ApiOperation("获取阿里云视频播放凭证")
    public Result getVideoPlayAuth(@PathVariable String videoSourceId){

        try {
            //获取密钥
            String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
            String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;

            //获取客户端对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoSourceId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //获取播放凭证
            String playAuth = response.getPlayAuth();
            System.out.println(playAuth);

            return Result.ok().message("获取播放凭证成功").data("playAuth",playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取播放凭证失败");
        }
    }
}
