package com.adventurer.jwtintegration.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.adventurer.jwtintegration.config.JWTConstants.AUTHORITIES_KEY;
import static com.adventurer.jwtintegration.config.JWTConstants.EXPIRATION_DATE;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

@Component
public class TokenProvider {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${security.jwt.token.secretkey}")
    private String secretKey;

    @Value("${security.jwt.token.expiration}")
    private int expiration;

    @Autowired
    private UserDetailsService userDetailsService;


    public String createToken(Authentication authentication) throws IOException {
        Date validity = new DateTime().plusSeconds(expiration).toDate();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, "")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(validity)
                .compact();
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(EXPIRATION_DATE, generateCurrentDate());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }


    public Authentication getAuthentication(String token) throws IOException {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(claims.getSubject()), "", null);
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: " + e.getMessage());
            return false;
        }
    }


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    private Date generateCurrentDate() {
        return new DateTime().plusSeconds(expiration).toDate();
    }
}
