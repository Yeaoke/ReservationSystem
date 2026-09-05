package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.app.repos")
@EntityScan(basePackages = "com.example.app.models")
public class ReservationSystemApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ReservationSystemApplication.class);
		app.setAdditionalProfiles("dev");
		app.run(args);
	}
}
