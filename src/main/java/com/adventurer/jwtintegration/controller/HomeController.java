package com.adventurer.jwtintegration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Claudia López
 * @Author Diego Sepúlveda
 */

@RestController()
public class HomeController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "HOLA";
    }
}
