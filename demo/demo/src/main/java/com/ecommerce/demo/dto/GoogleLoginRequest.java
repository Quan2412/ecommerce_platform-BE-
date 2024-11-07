package com.ecommerce.demo.dto;

import lombok.Data;

@Data
public class GoogleLoginRequest {
    private String token;
    private String email;
    private String name;


    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}