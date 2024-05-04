package com.example.rvlab1.restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateProvider {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
