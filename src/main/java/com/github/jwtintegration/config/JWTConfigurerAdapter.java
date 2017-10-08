package com.github.jwtintegration.config;

import com.github.jwtintegration.filter.JWTFilter;
import com.github.jwtintegration.service.AuthenticationService;
import com.github.jwtintegration.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author Claudia López
 * @author Diego Sepúlveda
 */

@Component
public class JWTConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JWTFilter customFilter = new JWTFilter(tokenService, authenticationService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
