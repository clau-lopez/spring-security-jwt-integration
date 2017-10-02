package com.adventurer.jwtintegration.controller;

import com.adventurer.jwtintegration.domain.AuthenticationRequest;
import com.adventurer.jwtintegration.domain.AuthenticationResponse;
import com.adventurer.jwtintegration.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
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
