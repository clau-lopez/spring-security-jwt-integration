package com.adventurer.jwtintegration.service;

import com.adventurer.jwtintegration.domain.AuthenticationRequest;
import com.adventurer.jwtintegration.domain.AuthenticationResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws IOException;

    AuthenticationResponse refresh(HttpServletRequest request);

    boolean validateToken(HttpServletRequest request);
}
