package com.nicklaus.serviceucenter.controller;


import com.nicklaus.commonutils.JwtUtils;
import com.nicklaus.commonutils.Result;
import com.nicklaus.serviceucenter.entity.UcenterMember;
import com.nicklaus.serviceucenter.entity.vo.LoginVo;
import com.nicklaus.serviceucenter.entity.vo.RegisterVo;
import com.nicklaus.serviceucenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-19
 */
@RestController
@RequestMapping("/serviceUCenter/member")
//@CrossOrigin
@Api(tags = "用户信息管理")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return Result.ok().data("token", token);
    }

    @PostMapping("register")
    @ApiOperation("用户注册")
    public Result register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Result.ok();
    }

    @GetMapping("auth/getLoginInfo")
    @ApiOperation("根据token值获取登录用户数据")
    public Result getLoginInfo(HttpServletRequest request){
        //获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        return Result.ok().data("item", member);
    }

    @GetMapping("getMemberById/{memberId}")
    @ApiOperation("根据用户id获取用户信息")
    public com.nicklaus.commonutils.UcenterMember getMemberById(@PathVariable String memberId){
        com.nicklaus.commonutils.UcenterMember member = new com.nicklaus.commonutils.UcenterMember();
        BeanUtils.copyProperties(memberService.getById(memberId),member);
        return member;
    }

    @GetMapping("countRegistration/{day}")
    @ApiOperation("根据日期统计当天注册的人数")
    public Result countRegistration(@PathVariable(name = "day") String day){
        int count = memberService.countRegistrationByDay(day);
        return Result.ok().data("countRegistration", count);
    }
}

