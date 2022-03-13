package com.dt002g.reviewapplication.backend.util;

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
		System.out.println("Environment loaded: " + (environment == env));
	}
	
	public static String getDatabaseDialect() {
		String url = environment.getProperty("spring.datasource.url");
		int firstIndex = url.indexOf(":");
		int secondIndex = url.indexOf(":", firstIndex + 1);
		String dialect = url.substring(firstIndex + 1, secondIndex);
		System.out.println(dialect);
		return dialect;
	}
}