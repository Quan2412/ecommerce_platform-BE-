package com.ecommerce.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.ecommerce.demo.entity"})  // Specify correct package
@EnableJpaRepositories(basePackages = {"com.ecommerce.demo.repository"})  // Fix package name
public class EccomercePlatformDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EccomercePlatformDemoApplication.class, args);
    }
}