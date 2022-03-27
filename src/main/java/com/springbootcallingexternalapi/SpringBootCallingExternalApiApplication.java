package com.springbootcallingexternalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;

@Configuration
@SpringBootApplication()
public class SpringBootCallingExternalApiApplication {

	public static void main(String[] args) {
		//Subir:
		//Nueva informacion
		//Estar en la rama adecuada
		//git add -A
		//git commit -m ""
		//git push
		//realizar pull request
		//
		//Traer:
		//pararse en la rama que queremos traer
		//git fetch (trae todas las ramas)
		//git pull (Rama especifica)
		//realizar pull request
		//Merge del pull request
		SpringApplication.run(SpringBootCallingExternalApiApplication.class, args);
	}
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
