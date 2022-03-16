package com.dt002g.reviewapplication.backend.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration

public class QueryChooser {

	@Autowired
	private Environment env;
	
	private static Environment environment;
	
	@PostConstruct
	public void init() {
		environment = env;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now) + " Environment loaded: " + (environment == env));
	}
	
	public static String getDatabaseDialect() {
		String url = environment.getProperty("spring.datasource.url");
		int firstIndex = url.indexOf(":");
		int secondIndex = url.indexOf(":", firstIndex + 1);
		String dialect = url.substring(firstIndex + 1, secondIndex);
		return dialect;
	}
}