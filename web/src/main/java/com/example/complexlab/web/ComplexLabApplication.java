package com.example.complexlab.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.complexlab")
@EnableJpaRepositories(basePackages = "com.example.complexlab.persistence.repository")
@EntityScan(basePackages = "com.example.complexlab.core.model")
public class ComplexLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComplexLabApplication.class, args);
    }
}
