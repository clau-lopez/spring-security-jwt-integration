package com.adventurer.jwtintegration.filter;

import com.adventurer.jwtintegration.config.JWTConstants;
import com.adventurer.jwtintegration.token.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
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

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

public class JWTFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);
    private TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String jwt = resolveToken(httpServletRequest);
            if (!StringUtils.isBlank(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException eje) {
            log.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWTConstants.AUTHORIZATION_HEADER);
        if (!StringUtils.isBlank(bearerToken)) {
            return bearerToken;
        } else {
            String token = request.getParameter(JWTConstants.AUTHORIZATION_HEADER);
            if (!StringUtils.isBlank(token)) {
                return token;
            }
        }
        return EMPTY;
    }
}
