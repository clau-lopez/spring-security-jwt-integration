package com.adventurer.jwtintegration.filter;

import com.adventurer.jwtintegration.config.JWTConstants;
import com.adventurer.jwtintegration.service.AuthenticationService;
import com.adventurer.jwtintegration.token.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */
public class JWTFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    private TokenService tokenService;
    private AuthenticationService authenticationService;

    public JWTFilter(TokenService tokenService, AuthenticationService authenticationService) {
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            validateTokenAndSetInContext(getTokenFromRequest((HttpServletRequest) servletRequest));
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (ExpiredJwtException e) {
            log.info("Security exception for user {} - {}", e.getClaims().getSubject(), e.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void validateTokenAndSetInContext(String token) throws IOException {
        if (tokenService.isValidToken(token)) {
            setAuthenticationInSecurityContext(token);
        }
    }

    private void setAuthenticationInSecurityContext(String token) throws IOException {
        Authentication authentication = authenticationService.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(JWTConstants.AUTHORIZATION_HEADER);
    }
}
