package com.cafe24.shkim30;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(basePackages = "com.cafe24.shkim30")
public class ShBlogProjectApiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ShBlogProjectApiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ShBlogProjectApiApplication.class,args);
	}
}
