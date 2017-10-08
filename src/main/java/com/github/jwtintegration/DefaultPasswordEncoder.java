package com.github.jwtintegration;

import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author Claudia López
 * @author Diego Sepúlveda
 */

public class DefaultPasswordEncoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
