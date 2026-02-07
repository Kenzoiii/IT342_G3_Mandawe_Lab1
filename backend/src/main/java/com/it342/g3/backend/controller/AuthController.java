package com.it342.g3.backend.controller;

import com.it342.g3.backend.model.User;
import com.it342.g3.backend.service.AuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody RegisterRequest request) {
        Map<String, String> response = new HashMap<>();
        if(authService.getAllUsers().stream().anyMatch(u -> u.getUsername().equals(request.getUsername()))) {
            response.put("message", "Username already exists");
            return response;
        }
        if(authService.getAllUsers().stream().anyMatch(u -> u.getEmail().equals(request.getEmail()))) {
            response.put("message", "Email already exists");
            return response;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole() != null ? request.getRole() : "USER");

        authService.registerUser(user);
        response.put("message", "User registered successfully");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        Map<String, String> response = new HashMap<>();
        User user = authService.authenticateUser(request.getEmail(), request.getPassword());
        if(user == null) {
            response.put("message", "Invalid email or password");
            return response;
        }
        String token = authService.generateToken(user);
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        return response;
    }

    // DTOs
    @Data
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        private String role; // optional
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}
