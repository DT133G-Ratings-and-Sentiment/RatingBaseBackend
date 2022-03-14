package com.dt002g.reviewapplication.backend;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@SpringBootApplication

@PropertySource(value = "file:application.properties", ignoreResourceNotFound = true)
public class BackendApplication {
	public static void main(String[] args) throws IOException {

		SpringApplication.run(BackendApplication.class, args);
	}
	

}
