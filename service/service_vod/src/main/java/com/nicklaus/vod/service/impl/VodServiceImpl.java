package com.nicklaus.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.vod.service.VodService;
import com.nicklaus.vod.util.AliyunVodSDKUtils;
import com.nicklaus.vod.util.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;

            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();

            if (!response.isSuccess()){
                if (StringUtils.isEmpty(videoId)){
                    //上传失败
                    throw new GuliException(20001,"视频上传失败！");
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001,"视频上传失败！");
        }
    }

    @Override
    public void deleteVideoByVideoId(String videoId) {
        try {
            //创建客户端
            String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
            String keySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, keySecret);

            //创建request,response对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();

            //设置视频唯一id
            request.setVideoIds(videoId);
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(20001,"删除视频失败！");
        }
    }

    @Override
    public void deleteVideoList(List<String> videoIdList) {
        try {
            //创建客户端
            String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
            String keySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, keySecret);

            //创建request,response对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();

            String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");

            //设置视频唯一id
            request.setVideoIds(videoIds);
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(20001,"删除视频失败！");
        }
    }
}
