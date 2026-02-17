package com.it342.g3.backend.controller;

import com.it342.g3.backend.model.User;
import com.it342.g3.backend.service.AuthService;
import com.it342.g3.backend.service.TokenBlacklist;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
        Map<String, String> response = new HashMap<>();
        // Basic validation
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            response.put("message", "Username is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            response.put("message", "Email is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            response.put("message", "Password must be at least 6 characters");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (authService.getAllUsers().stream().anyMatch(u -> u.getUsername().equals(request.getUsername()))) {
            response.put("message", "Username already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        if (authService.getAllUsers().stream().anyMatch(u -> u.getEmail().equals(request.getEmail()))) {
            response.put("message", "Email already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole() != null ? request.getRole() : "USER");

        authService.registerUser(user);
        response.put("message", "User registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        Map<String, String> response = new HashMap<>();
        if (request.getEmail() == null || request.getPassword() == null) {
            response.put("message", "Email and password are required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        User user = authService.authenticateUser(request.getEmail(), request.getPassword());
        if (user == null) {
            response.put("message", "Invalid email or password");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        String token = authService.generateToken(user);
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        Map<String, String> response = new HashMap<>();
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.put("message", "Authorization header missing");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String token = authorization.substring("Bearer ".length());
        tokenBlacklist.blacklist(token);
        response.put("message", "Logged out successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
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
