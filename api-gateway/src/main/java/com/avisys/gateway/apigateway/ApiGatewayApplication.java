package com.avisys.gateway.apigateway;

import javax.ws.rs.core.Application;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

//@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
	
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context =
				SpringApplication.run(ApiGatewayApplication.class, args);
	}

	public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = 
            		SpringApplication.run(ApiGatewayApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }
}
