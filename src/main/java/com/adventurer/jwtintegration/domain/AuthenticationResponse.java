package com.adventurer.jwtintegration.domain;


import lombok.Data;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

public @Data
class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
