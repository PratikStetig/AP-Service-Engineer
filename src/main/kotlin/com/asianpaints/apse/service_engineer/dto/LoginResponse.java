package com.asianpaints.apse.service_engineer.dto;

import java.util.List;

public class LoginResponse {
    private String username;
    private String jwtToken;
    private List<String> roles;

    public LoginResponse(String username, String jwtToken, List<String> roles) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public List<String> getRoles() {
        return roles;
    }
}
