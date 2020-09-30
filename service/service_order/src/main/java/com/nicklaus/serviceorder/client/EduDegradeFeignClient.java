package com.nicklaus.serviceorder.client;

import com.nicklaus.commonutils.ordervo.OrderCourseVo;
import org.springframework.stereotype.Component;

@Component
public class EduDegradeFeignClient implements EduClient {
    @Override
    public OrderCourseVo findOrderCourseInfo(String courseId) {
        return new OrderCourseVo();
    }
}
