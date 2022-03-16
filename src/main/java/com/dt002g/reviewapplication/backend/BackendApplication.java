package com.dt002g.reviewapplication.backend;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication

@PropertySource(value = "file:application.properties", ignoreResourceNotFound = true)
public class BackendApplication {
	public static void main(String[] args) throws IOException {

		SpringApplication.run(BackendApplication.class, args);
	}
}