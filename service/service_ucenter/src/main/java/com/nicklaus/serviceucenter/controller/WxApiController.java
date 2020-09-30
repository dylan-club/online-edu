package com.nicklaus.serviceucenter.controller;

import com.google.gson.Gson;
import com.nicklaus.commonutils.JwtUtils;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceucenter.entity.UcenterMember;
import com.nicklaus.serviceucenter.service.UcenterMemberService;
import com.nicklaus.serviceucenter.utils.ConstantUtils;
import com.nicklaus.serviceucenter.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
//@CrossOrigin
@Api(tags = "微信登录管理")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("login")
    @ApiOperation("获取微信二维码")
    public String login(){
        //拼接获取二维码的地址
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirectUrl进行转码
        String redirectUrl = ConstantUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        String qrCodeUrl = String.format(baseUrl,
                ConstantUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "nicklaus");

        return "redirect:"+qrCodeUrl;
    }

    @GetMapping("callback")
    @ApiOperation("获取微信的state和code")
    public String callback(String code, String state, HttpSession session){
        try {
            String baseUrl = "https://api.weixin.qq.com/sns/oauth2/" +
                    "access_token?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String url = String.format(baseUrl,
                    ConstantUtils.WX_OPEN_APP_ID,
                    ConstantUtils.WX_OPEN_APP_SECRET,
                    code);

            //使用httpClient发送get请求，获取响应字符串
            String result = HttpClientUtils.get(url);
            //使用gson进行json字符串转换
            Gson gson = new Gson();
            HashMap resultMap = gson.fromJson(result, HashMap.class);
            String access_token = (String) resultMap.get("access_token");
            String openid = (String) resultMap.get("openid");

            //根据openid查询该用户是否存在
            UcenterMember member = memberService.findByOpenId(openid);

            if (member == null){
                //用户未注册，查询用户数据并存入数据库
                String userBaseUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                        "access_token=%s" +
                        "&openid=%s";
                String userUrl = String.format(userBaseUrl,access_token,openid);
                //请求url
                String userInfoStr = HttpClientUtils.get(userUrl);

                //将json字符串转换成map
                HashMap userInfoMap = gson.fromJson(userInfoStr, HashMap.class);
                //获取数据
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);

                //将用户信息存入数据库
                memberService.save(member);
            }

            //获取jwt令牌
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token="+token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
