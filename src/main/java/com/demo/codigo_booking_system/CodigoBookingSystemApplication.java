package com.demo.codigo_booking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.demo.codigo_booking_system")
@EnableScheduling
@EnableCaching
public class CodigoBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodigoBookingSystemApplication.class, args);
	}

}
