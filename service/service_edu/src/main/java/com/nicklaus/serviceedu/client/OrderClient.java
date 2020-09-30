package com.nicklaus.serviceedu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-order", fallback = OrderDegradeFeignClient.class)
@Component("orderClient")
public interface OrderClient {

    @GetMapping("/serviceOrder/order/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable(name = "memberId") String memberId, @PathVariable(name = "courseId") String courseId);
}
