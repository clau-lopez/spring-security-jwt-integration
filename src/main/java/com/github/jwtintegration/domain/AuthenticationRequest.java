package com.github.jwtintegration.domain;


import lombok.Data;

/**
 * @author Claudia López
 * @author Diego Sepúlveda
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
