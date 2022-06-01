package com.springbootcallingexternalapi;

import static org.springframework.context.annotation.FilterType.CUSTOM;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * This is the api main class.
 */
@Configuration
@EnableScheduling
@SpringBootApplication()
@ComponentScan(excludeFilters = {
    @ComponentScan.Filter(type = CUSTOM, classes = TypeExcludeFilter.class)})
@EnableCaching

public class SpringBootCallingExternalApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootCallingExternalApiApplication.class, args);
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
