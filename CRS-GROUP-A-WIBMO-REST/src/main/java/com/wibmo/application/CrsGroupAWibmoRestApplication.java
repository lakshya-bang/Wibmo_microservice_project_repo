package com.wibmo.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan("com.wibmo.*")
@EnableWebMvc
public class CrsGroupAWibmoRestApplication {
	
	private static final Logger logger = LogManager.getLogger(CrsGroupAWibmoRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupAWibmoRestApplication.class, args);
		logger.info("Application Started!");
	}

}
