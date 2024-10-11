package com.cohort_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CohortManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CohortManagementApplication.class, args);
	}

}
