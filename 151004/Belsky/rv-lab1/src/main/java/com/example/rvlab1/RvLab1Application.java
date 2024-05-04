package com.example.rvlab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class RvLab1Application {

    public static void main(String[] args) {
        SpringApplication.run(RvLab1Application.class, args);
    }

}
