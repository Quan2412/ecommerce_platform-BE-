package com.ecommerce.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Configuration
public class GoogleConfig {
    
    @Bean
    public GsonFactory gsonFactory() {
        return GsonFactory.getDefaultInstance();
    }
    
    @Bean
    public NetHttpTransport netHttpTransport() {
        return new NetHttpTransport();
    }
}