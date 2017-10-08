package com.github.jwtintegration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Claudia López
 * @author Diego Sepúlveda
 */

@RestController()
public class HomeController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "HOLA";
    }
}
