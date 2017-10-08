package com.github.jwtintegration.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Component
@Validated
@ConfigurationProperties(prefix = "security.jwt")
public @Data
class JWTSecurityProperties {

    private final Logger logger = LoggerFactory.getLogger(JWTSecurityProperties.class);


    /**
     * Key used to signed the token
     */
    @NotNull
    private String secretKey;

    /**
     * Time to expiration a token in millisenconds
     */
    @NotNull
    private int expirationDate;

    private String defaultUser = "admin";

    private String defaultPassword;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(defaultPassword)) {
            defaultPassword = UUID.randomUUID().toString();
            logger.info("Using default password {}", defaultPassword);
        }
    }
}
