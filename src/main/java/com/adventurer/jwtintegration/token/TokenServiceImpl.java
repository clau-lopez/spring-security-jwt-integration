package com.adventurer.jwtintegration.token;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

@Service
public class TokenServiceImpl implements TokenService {

    private final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Value("${security.jwt.token.secretkey}")
    private String secretKey;

    @Value("${security.jwt.token.expiration}")
    private int expiration;


    @Override
    public String createToken(Authentication authentication) throws IOException {
        return generateToken(new HashMap<>()).setSubject(authentication.getName()).compact();
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
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(Date expiration) {
        return getCurrentDate().before(expiration);
    }

    private JwtBuilder generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secretKey);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Date generateExpirationDate() {
        return new DateTime().plusSeconds(expiration).toDate();
    }

    private Date getCurrentDate() {
        return new DateTime().toDate();
    }
}
