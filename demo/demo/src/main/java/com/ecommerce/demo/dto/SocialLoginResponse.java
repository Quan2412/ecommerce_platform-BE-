package com.ecommerce.demo.dto;

import lombok.Data;

@Data
public class SocialLoginResponse {
    private String token;
    private UserDTO user;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return this.user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
    
}