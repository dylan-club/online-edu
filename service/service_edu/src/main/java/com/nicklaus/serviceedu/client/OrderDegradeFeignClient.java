package com.nicklaus.serviceedu.client;

import org.springframework.stereotype.Component;

@Component
public class OrderDegradeFeignClient implements OrderClient {
    @Override
    public Boolean isBuyCourse(String memberId, String courseId) {
        return true;
    }
}
