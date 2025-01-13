package com.yankovich.dc_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DcRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DcRestApplication.class, args);
	}

}
