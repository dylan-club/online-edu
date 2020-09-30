package com.nicklaus.servicemsm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.nicklaus.servicemsm.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean sendAliyunMsg(Map<String, Object> params, String phoneNumber) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GGTnuUy5eFmKBJkyhBg", "X4ulTdYoeJzYFDvK9SoqXew7LoSQIS");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");


        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "我的在线教育网站");
        request.putQueryParameter("TemplateCode", "SMS_202812213");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params));
        try {
            CommonResponse response = client.getCommonResponse(request);

            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
