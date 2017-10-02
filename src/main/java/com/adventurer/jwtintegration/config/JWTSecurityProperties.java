package com.adventurer.jwtintegration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "adventurer.security.jwt")
public @Data
class JWTSecurityProperties {

    /**
     *
     */
    private String secretKey;

    /**
     * Time to expiration a token in millisenconds
     */
    private int expirationDate;

}
