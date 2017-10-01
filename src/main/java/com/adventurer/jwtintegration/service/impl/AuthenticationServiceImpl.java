package com.adventurer.jwtintegration.service.impl;

import com.adventurer.jwtintegration.domain.AuthenticationRequest;
import com.adventurer.jwtintegration.domain.AuthenticationResponse;
import com.adventurer.jwtintegration.service.AuthenticationService;
import com.adventurer.jwtintegration.token.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.adventurer.jwtintegration.config.JWTConstants.AUTHORIZATION_HEADER_REFRESH;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws IOException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthenticationResponse(tokenProvider.createToken(authentication));
    }

    @Override
    public AuthenticationResponse refresh(HttpServletRequest request) {
        return new AuthenticationResponse(tokenProvider.refreshToken(request.getHeader(AUTHORIZATION_HEADER_REFRESH)));
    }

    @Override
    public boolean validateToken(HttpServletRequest request) {
        return tokenProvider.validateToken(request.getHeader(AUTHORIZATION_HEADER_REFRESH));
    }
}
