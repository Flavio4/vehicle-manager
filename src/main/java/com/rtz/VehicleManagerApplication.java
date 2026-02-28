package com.rtz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.rtz.vehicle_manager", "com.rtz.authentication_manager"})
public class VehicleManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleManagerApplication.class, args);
	}

}
