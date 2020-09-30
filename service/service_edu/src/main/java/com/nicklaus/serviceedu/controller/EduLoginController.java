package com.nicklaus.serviceedu.controller;

import com.nicklaus.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("serviceEdu/user")
//@CrossOrigin
@Api(tags = "系统登录")
public class EduLoginController {

    @PostMapping("login")
    @ApiOperation("用户登录")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    @GetMapping("info")
    @ApiOperation("用户信息")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @PostMapping("logout")
    @ApiOperation("用户退出")
    public Result logout(){
        System.out.println("************在线教育后台系统管理员已退出*************");
        return Result.ok();
    }
}
