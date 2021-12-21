package com.ndayal.my_hiit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// TODO: Add CORS support
// TODO: Enable security, authorization and authentication
@SpringBootApplication
public class MyHiitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyHiitApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {

				registry.addMapping("/v1/exercises")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("GET");

				registry.addMapping("/v1/workouts*")
						.allowedOrigins("http://localhost:3000");

				registry.addMapping("/v1/users")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("GET");
			}
		};
	}
}
