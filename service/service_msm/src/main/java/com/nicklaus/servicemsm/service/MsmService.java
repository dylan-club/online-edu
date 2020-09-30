package com.nicklaus.servicemsm.service;

import java.util.Map;

public interface MsmService {
    boolean sendAliyunMsg(Map<String, Object> params, String phoneNumber);
}
