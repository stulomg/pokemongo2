package com.springbootapp.springbootcallingexternalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class SpringBootCallingExternalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCallingExternalApiApplication.class, args);
	}

	@RequestMapping ("/hello")
	public String hello() {
		return "Hello World";
	}

}
