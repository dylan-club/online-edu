package com.nicklaus.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideoByVideoId(String videoId);

    void deleteVideoList(List<String> videoIdList);
}
