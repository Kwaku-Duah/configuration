package com.specialization_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpecializationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpecializationManagementApplication.class, args);
	}

}
