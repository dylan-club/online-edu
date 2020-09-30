package com.nicklaus.servicecms.service;

import com.nicklaus.servicecms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-17
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllBanners();
}
