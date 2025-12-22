package com.testefintech.accounts.controller;

import com.testefintech.accounts.model.User;
import com.testefintech.accounts.service.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        return authService.register(email, password);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        String token = authService.login(email, password);

        return Map.of("token", token);
    }
}
