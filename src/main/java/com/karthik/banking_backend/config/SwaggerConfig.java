package com.karthik.banking_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI bankingOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Banking Management System API")
                        .version("1.0")
                        .description("Spring Boot Banking Application with JWT Authentication")
                        .contact(new Contact()
                                .name("Edara Karthik")
                                .email("karthik@gmail.com")));
    }
}