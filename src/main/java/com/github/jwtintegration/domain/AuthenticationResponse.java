package com.github.jwtintegration.domain;


import lombok.Data;

/**
 * @author Claudia López
 * @author Diego Sepúlveda
 */

public @Data
class AuthenticationResponse {

    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
