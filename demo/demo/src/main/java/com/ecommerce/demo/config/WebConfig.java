package com.ecommerce.demo.config;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("http://localhost:8080"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}