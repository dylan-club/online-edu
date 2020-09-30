package com.nicklaus.servicecms.controller;


import com.nicklaus.commonutils.Result;
import com.nicklaus.servicecms.entity.CrmBanner;
import com.nicklaus.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/serviceCms/bannerFront")
@Api(tags = "轮播图前台管理")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("findAllBanner")
    @ApiOperation("获取首页面的轮播图")
    public Result findAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanners();
        return Result.ok().data("list",list);
    }
}

