package com.example.product.management.controller;

import com.example.product.management.entity.Users;
import com.example.product.management.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String register(@RequestBody Users user) {
        authService.register(user);
        return "User registered successfully";
    }
}