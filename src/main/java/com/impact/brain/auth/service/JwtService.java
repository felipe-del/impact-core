package com.impact.brain.auth.service;

import com.impact.brain.user.entity.User;
import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtService {
    String generateToken(User user);
    boolean validateToken(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String getUserEmailFromToken(String token);
    void blacklistToken(String token);
}
