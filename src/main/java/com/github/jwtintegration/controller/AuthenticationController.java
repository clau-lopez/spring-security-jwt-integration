package com.github.jwtintegration.controller;

import com.github.jwtintegration.domain.AuthenticationRequest;
import com.github.jwtintegration.domain.AuthenticationResponse;
import com.github.jwtintegration.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Claudia López
 * @author Diego Sepúlveda
 */

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException, IOException {
        return authenticationService.authenticate(authenticationRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/refresh")
    public AuthenticationResponse refreshToken(HttpServletRequest request) {
        return authenticationService.refresh(request);
    }


    @GetMapping(value = "/validate")
    public void validateToken(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(authenticationService.validateToken(request) ? HttpStatus.OK.value() : HttpStatus.UNAUTHORIZED.value());
    }
}
