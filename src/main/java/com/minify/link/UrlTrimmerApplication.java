package com.minify.link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "com.minify")
@ComponentScan(basePackages = "com.minify")
public class UrlTrimmerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlTrimmerApplication.class, args);
	}

}
