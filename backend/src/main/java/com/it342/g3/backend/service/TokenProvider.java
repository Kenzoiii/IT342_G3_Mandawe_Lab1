package com.it342.g3.backend.service;

import com.it342.g3.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private static final String PREFIX = "token-for-";

    @Autowired
    private TokenBlacklist tokenBlacklist;

    public String generateToken(User user) {
        // Simple token format for this lab
        return PREFIX + user.getUserId();
    }

    public boolean validateToken(String token) {
        // Reject blacklisted tokens
        if (token == null || tokenBlacklist.isBlacklisted(token)) {
            return false;
        }
        // Basic format validation: token-for-<id>
        if (!token.startsWith(PREFIX)) {
            return false;
        }
        try {
            String idStr = token.substring(PREFIX.length());
            Long.parseLong(idStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
