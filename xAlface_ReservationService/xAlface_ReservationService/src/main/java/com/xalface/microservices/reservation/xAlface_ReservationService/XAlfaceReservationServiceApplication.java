package com.xalface.microservices.reservation.xAlface_ReservationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.xalface.microservices.reservation.xAlface_ReservationService.clients")
public class XAlfaceReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(XAlfaceReservationServiceApplication.class, args);
	}

}
