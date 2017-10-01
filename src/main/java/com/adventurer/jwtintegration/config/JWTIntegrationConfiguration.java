package com.adventurer.jwtintegration.config;

import com.adventurer.jwtintegration.DefaultPasswordEncoder;
import com.adventurer.jwtintegration.DefaultUserDetailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

@Configuration
public class JWTIntegrationConfiguration {


    @ConditionalOnMissingBean(value = UserDetailsService.class)
    @Bean
    public UserDetailsService detailsService() {
        return new DefaultUserDetailService();
    }

    @ConditionalOnMissingBean(value = WebSecurityConfigurerAdapter.class)
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new DefaultWebSecurityConfig();
    }

    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DefaultPasswordEncoder();
    }

}
