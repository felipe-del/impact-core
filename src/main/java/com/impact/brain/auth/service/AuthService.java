package com.impact.brain.auth.service;

import com.impact.brain.auth.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    String login(LoginRequest loginRequest, HttpServletRequest httpServletRequest);
}
