package com.xalface.microservices.auth.xAlface_AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class XAlfaceAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(XAlfaceAuthServiceApplication.class, args);
	}

}
