package com.nicklaus.servicestatistics.client;

import com.nicklaus.commonutils.Result;
import org.springframework.stereotype.Component;

@Component
public class UserDegradeFeignClient implements UserClient {
    @Override
    public Result countRegistration(String day) {
        return Result.error().data("countRegistration", -1);
    }
}
