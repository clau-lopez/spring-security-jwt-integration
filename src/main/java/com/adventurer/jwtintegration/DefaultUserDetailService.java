package com.adventurer.jwtintegration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

public class DefaultUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if ("admin".equals(s)) {
            return new User("admin", "123", Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
