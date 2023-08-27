package com.wibmo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaRepositories("com.wibmo.repository")
@EntityScan("com.wibmo.entity")
@EnableAutoConfiguration
@Configuration
@ComponentScan("com.wibmo.*")
@EnableWebMvc
@SpringBootApplication
public class CrsGroupAWibmoJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupAWibmoJpaApplication.class, args);
	}

}
