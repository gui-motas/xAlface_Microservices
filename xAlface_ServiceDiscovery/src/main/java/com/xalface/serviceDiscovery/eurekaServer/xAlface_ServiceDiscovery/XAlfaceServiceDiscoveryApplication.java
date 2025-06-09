package com.xalface.serviceDiscovery.eurekaServer.xAlface_ServiceDiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class XAlfaceServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(XAlfaceServiceDiscoveryApplication.class, args);
	}

}
