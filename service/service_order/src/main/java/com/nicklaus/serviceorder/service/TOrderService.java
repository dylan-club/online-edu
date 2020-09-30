package com.nicklaus.serviceorder.service;

import com.nicklaus.serviceorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-24
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberId);
}
