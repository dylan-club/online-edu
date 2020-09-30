package com.nicklaus.servicestatistics.client;

import com.nicklaus.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter", fallback = UserDegradeFeignClient.class)
@Component
public interface UserClient {

    @GetMapping("/serviceUCenter/member/countRegistration/{day}")
    public Result countRegistration(@PathVariable(name = "day") String day);
}
