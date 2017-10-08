package com.github.jwtintegration.token.impl;

import com.github.jwtintegration.config.JWTSecurityProperties;
import com.github.jwtintegration.exception.JWTIntegrationException;
import com.github.jwtintegration.token.TokenService;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Claudia López
 * @author Diego Sepúlveda
 */

@Service
public class TokenServiceImpl implements TokenService {

    public static final String AUTHORITIES = "authorities";
    private final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private JWTSecurityProperties JWTSecurityProperties;


    @Override
    public String createToken(Authentication authentication) throws IOException {
        return generateToken(new HashMap<>()).claim(AUTHORITIES, authentication.getAuthorities()).setSubject(authentication.getName()).compact();
    }

    @Override
    public String refreshToken(String token) {
        return generateToken(getClaimsFromToken(token)).compact();
    }


    @Override
    public boolean isValidToken(String token) {
        try {
            return !StringUtils.isBlank(token) && isTokenExpired(getExpirationDateFromToken(token));
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        if (!StringUtils.isBlank(token)) {
            return Jwts.parser()
                    .setSigningKey(JWTSecurityProperties.getSecretKey())
                    .parseClaimsJws(token).getBody();
        } else {
            throw new JWTIntegrationException("Token is not valid");
        }
    }

    public boolean isTokenExpired(Date expiration) {
        return getCurrentDate().before(expiration);
    }

    private JwtBuilder generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, JWTSecurityProperties.getSecretKey());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Date generateExpirationDate() {
        return new DateTime().plusSeconds(JWTSecurityProperties.getExpirationDate()).toDate();
    }

    private Date getCurrentDate() {
        return new DateTime().toDate();
    }
}
