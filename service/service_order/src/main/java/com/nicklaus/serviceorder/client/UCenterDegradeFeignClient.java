package com.nicklaus.serviceorder.client;

import com.nicklaus.commonutils.UcenterMember;
import org.springframework.stereotype.Component;

@Component
public class UCenterDegradeFeignClient implements UCenterClient {
    @Override
    public UcenterMember getMemberById(String memberId) {
        return new UcenterMember();
    }
}
