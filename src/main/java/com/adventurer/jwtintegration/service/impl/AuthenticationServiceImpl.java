package com.adventurer.jwtintegration.service.impl;

import com.adventurer.jwtintegration.domain.AuthenticationRequest;
import com.adventurer.jwtintegration.domain.AuthenticationResponse;
import com.adventurer.jwtintegration.service.AuthenticationService;
import com.adventurer.jwtintegration.token.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.adventurer.jwtintegration.config.JWTConstants.AUTHORIZATION_HEADER;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws IOException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthenticationResponse(tokenService.createToken(authentication));
    }

    @Override
    public AuthenticationResponse refresh(HttpServletRequest request) {
        return new AuthenticationResponse(tokenService.refreshToken(request.getHeader(AUTHORIZATION_HEADER)));
    }

    @Override
    public boolean validateToken(HttpServletRequest request) {
        return tokenService.isValidToken(request.getHeader(AUTHORIZATION_HEADER));
    }

    @Override
    public Authentication getAuthentication(String token) throws IOException {
        Claims claims = tokenService.getClaimsFromToken(token);
        return new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(claims.getSubject()), "", null);
    }

}
