package com.nicklaus.demo;


import com.nicklaus.commonutils.Result;
import com.nicklaus.servicestatistics.StatisticsApplication;
import com.nicklaus.servicestatistics.client.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StatisticsApplication.class})
public class client_test {

    @Autowired
    private UserClient userClient;

    @Test
    public void testUserClient(){
        Result result = userClient.countRegistration("2019-01-19");
        int count = (int)result.getData().get("countRegistration");
        System.out.println(count);
    }
}
