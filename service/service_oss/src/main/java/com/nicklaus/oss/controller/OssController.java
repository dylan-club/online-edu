package com.nicklaus.oss.controller;

import com.nicklaus.commonutils.Result;
import com.nicklaus.oss.serivce.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@CrossOrigin
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("uploadAvatar")
    public Result uploadOssFile(MultipartFile file){

        //上传文件并获取文件的url
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().data("url",url);
    }
}
