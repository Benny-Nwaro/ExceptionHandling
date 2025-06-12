package com.example.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.lms"})
public class HibernateLmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateLmsApplication.class, args);

	}
}
