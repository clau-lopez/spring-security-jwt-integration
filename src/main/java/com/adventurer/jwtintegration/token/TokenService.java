package com.adventurer.jwtintegration.token;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface TokenService {

    String createToken(Authentication authentication) throws IOException;

    String refreshToken(String token);

    boolean isValidToken(String token);

    Claims getClaimsFromToken(String token);
}
