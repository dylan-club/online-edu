package com.nicklaus.serviceorder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter", fallback = UCenterDegradeFeignClient.class)
@Component("uCenterClient")
public interface UCenterClient {

    @GetMapping("/serviceUCenter/member/getMemberById/{memberId}")
    public com.nicklaus.commonutils.UcenterMember getMemberById(@PathVariable(name = "memberId") String memberId);

}
