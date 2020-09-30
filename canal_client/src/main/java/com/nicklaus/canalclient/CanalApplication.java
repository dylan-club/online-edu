package com.nicklaus.canalclient;

import com.nicklaus.canalclient.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CanalApplication implements CommandLineRunner {

    @Resource
    private CanalClient canalClient;

    @Override
    public void run(String... args) throws Exception {
        canalClient.run();
    }

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }
}
