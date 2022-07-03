package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class IntuitDriverManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(IntuitDriverManagementSystemApplication.class, args);
	}
}
