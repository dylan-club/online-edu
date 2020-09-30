package com.nicklaus.serviceedu.controller;


import com.nicklaus.commonutils.Result;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceedu.client.VodClient;
import com.nicklaus.serviceedu.entity.EduVideo;
import com.nicklaus.serviceedu.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
@RestController
@RequestMapping("/serviceEdu/video")
//@CrossOrigin
@Api(tags = "课程小节管理")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @GetMapping("findVideo/{videoId}")
    @ApiOperation("查找课程小节")
    public Result findVideo(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return Result.ok().data("video",video);
    }

    @PostMapping("addVideo")
    @ApiOperation("增加课程小节")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return Result.ok();
    }

    @PutMapping("updateVideo")
    @ApiOperation("修改课程小节")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return Result.ok();
    }

    @DeleteMapping("removeVideo/{videoId}")
    @ApiOperation("删除视频小节")
    public Result removeVideo(@PathVariable String videoId){
        //根据小节id得到视频id
        EduVideo eduVideo = videoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        //删除视频
        if (!StringUtils.isEmpty(videoSourceId) && !"".equals(videoSourceId)){
            Result result = vodClient.deleteVideo(videoSourceId);
            if (result.getCode() == 20001){
                throw new GuliException(20001,"删除视频失败，熔断器启动...");
            }
        }
        //删除小节
        videoService.removeById(videoId);
        return Result.ok();
    }
}

