package com.avisys.microservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UtilityApplication {

	Logger LOGGER = LoggerFactory.getLogger(UtilityApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UtilityApplication.class, args);
	}
}
