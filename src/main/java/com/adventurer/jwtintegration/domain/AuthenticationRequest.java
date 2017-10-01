package com.adventurer.jwtintegration.domain;


import lombok.Data;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

public @Data
class AuthenticationRequest {

    private String username;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
