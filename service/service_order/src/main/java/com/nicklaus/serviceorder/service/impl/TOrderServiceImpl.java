package com.nicklaus.serviceorder.service.impl;

import com.nicklaus.commonutils.UcenterMember;
import com.nicklaus.commonutils.ordervo.OrderCourseVo;
import com.nicklaus.serviceorder.client.EduClient;
import com.nicklaus.serviceorder.client.UCenterClient;
import com.nicklaus.serviceorder.entity.TOrder;
import com.nicklaus.serviceorder.mapper.TOrderMapper;
import com.nicklaus.serviceorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nicklaus.serviceorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-24
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private UCenterClient uCenterClient;

    @Autowired
    private EduClient eduClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        //查询用户数据
        UcenterMember member = uCenterClient.getMemberById(memberId);
        //查询课程数据
        OrderCourseVo orderCourseInfo = eduClient.findOrderCourseInfo(courseId);

        //创建订单
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(orderCourseInfo.getTitle());
        order.setCourseCover(orderCourseInfo.getCover());
        order.setTeacherName(orderCourseInfo.getTeacherName());
        order.setTotalFee(orderCourseInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        //保存订单
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
