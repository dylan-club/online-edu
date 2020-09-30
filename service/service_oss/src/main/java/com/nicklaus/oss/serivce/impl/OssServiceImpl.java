package com.nicklaus.oss.serivce.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.nicklaus.oss.serivce.OssService;
import com.nicklaus.oss.utils.ConstantPropertiesUtils;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //文件名称
            String name = file.getOriginalFilename();

            //使用UUID获得唯一名称
            String uuid = UUID.randomUUID().toString().replace("-","");
            String filename = uuid+name;

            //根据日期进行分类
            //2020/09/11
            String filePath = new DateTime().toString("yyyy/MM/dd");
            filename = filePath+"/"+filename;

            //第一个参数bucket name
            //第二个参数文件名称
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //获取图片的url
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;

            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
