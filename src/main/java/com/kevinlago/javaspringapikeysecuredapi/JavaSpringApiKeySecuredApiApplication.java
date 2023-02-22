package com.kevinlago.javaspringapikeysecuredapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class JavaSpringApiKeySecuredApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringApiKeySecuredApiApplication.class, args);
	}

}
