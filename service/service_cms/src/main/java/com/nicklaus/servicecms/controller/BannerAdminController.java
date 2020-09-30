package com.nicklaus.servicecms.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.commonutils.Result;
import com.nicklaus.servicecms.entity.CrmBanner;
import com.nicklaus.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/serviceCms/bannerAdmin")
@Api(tags = "轮播图后台管理")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("pageBanner/{current}/{limit}")
    @ApiOperation("轮播图分页查询")
    public Result pageBanner(@PathVariable Long current, @PathVariable Long limit){

        Page<CrmBanner> page = new Page<CrmBanner>(current, limit);
        //分页查询
        bannerService.page(page,null);

        return Result.ok().data("items",page.getRecords()).data("total",page.getTotal());
    }

    @PostMapping("addBanner")
    @ApiOperation("添加轮播图")
    public Result addBanner(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return Result.ok();
    }

    @DeleteMapping("removeBanner/{bannerId}")
    @ApiOperation("删除轮播图")
    public Result removeBannerById(@PathVariable(name = "bannerId") String id){
        bannerService.removeById(id);
        return Result.ok();
    }

    @GetMapping("findBanner/{bannerId}")
    @ApiOperation("查询轮播图")
    public Result findBannerById(@PathVariable String bannerId){
        CrmBanner banner = bannerService.getById(bannerId);
        return Result.ok().data("banner",banner);
    }

    @PutMapping("updateBanner")
    @ApiOperation("更新轮播图")
    public Result updateBanner(@RequestBody CrmBanner banner){
        bannerService.updateById(banner);
        return Result.ok();
    }
}

