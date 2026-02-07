package com.it342.g3.backend.service;

import com.it342.g3.backend.model.User;
import com.it342.g3.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() == null) user.setRole("USER");
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

    public String generateToken(User user) {
        return tokenProvider.generateToken(user);
    }

    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getProfile(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
