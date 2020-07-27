package com.tmorris.reunitedtrav;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
@EnableWebMvc
public class ReunitedTravApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReunitedTravApplication.class, args);
	}

}
