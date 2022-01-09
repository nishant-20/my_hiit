package com.ndayal.my_hiit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// TODO: Create Admin Tool
// TODO: Add CORS support
// TODO: Enable security, authorization and authentication
// TODO: Enable role management
// TODO: Add Custom exceptions and Exception Controller
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
				registry.addMapping("/v1/exercise")
						.allowedOrigins("http://localhost:3000", "https://nishant-20.github.io")
						.allowedMethods("GET");

				registry.addMapping("/v1/workout/**")
						.allowedOrigins("http://localhost:3000", "https://nishant-20.github.io")
						.allowedMethods("GET", "POST", "PUT", "DELETE");

				registry.addMapping("/v1/workoutHistories/**")
						.allowedOrigins("http://localhost:3000", "https://nishant-20.github.io")
						.allowedMethods("GET", "POST");

				registry.addMapping("/v1/user")
						.allowedOrigins("http://localhost:3000", "https://nishant-20.github.io")
						.allowedMethods("GET");
			}
		};
	}
}
