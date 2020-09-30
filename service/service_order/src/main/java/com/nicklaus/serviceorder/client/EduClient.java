package com.nicklaus.serviceorder.client;

import com.nicklaus.commonutils.ordervo.OrderCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-edu", fallback = EduDegradeFeignClient.class)
@Component("eduClient")
public interface EduClient {

    @GetMapping("/serviceEdu/course/findOrderCourseInfo/{courseId}")
    public OrderCourseVo findOrderCourseInfo(@PathVariable(name = "courseId") String courseId);
}
