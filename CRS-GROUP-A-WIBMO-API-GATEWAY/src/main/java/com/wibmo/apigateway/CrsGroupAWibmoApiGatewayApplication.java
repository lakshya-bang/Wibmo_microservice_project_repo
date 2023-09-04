package com.wibmo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.wibmo.routeconfiguration.SpringCloudConfiguration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@Import({SpringCloudConfiguration.class})
@EnableSwagger2
@EnableEurekaClient
public class CrsGroupAWibmoApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupAWibmoApiGatewayApplication.class, args);
	}

}
