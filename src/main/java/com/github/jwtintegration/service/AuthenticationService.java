package com.github.jwtintegration.service;

import com.github.jwtintegration.domain.AuthenticationRequest;
import com.github.jwtintegration.domain.AuthenticationResponse;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Claudia López
 * @author Diego Sepúlveda
 */

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws IOException;

    AuthenticationResponse refresh(HttpServletRequest request);

    boolean validateToken(HttpServletRequest request);

    Authentication getAuthentication(String token) throws IOException;
}
