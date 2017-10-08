package com.github.jwtintegration;

import com.github.jwtintegration.config.JWTSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

/**
 * @author Claudia López
 * @author Diego Sepúlveda
 */

public class DefaultUserDetailService implements UserDetailsService {

    @Autowired
    private JWTSecurityProperties jwtSecurityProperties;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (jwtSecurityProperties.getDefaultUser().equals(s)) {
            return new User(jwtSecurityProperties.getDefaultUser(), jwtSecurityProperties.getDefaultPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
