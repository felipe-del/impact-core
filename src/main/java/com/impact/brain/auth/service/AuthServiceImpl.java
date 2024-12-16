package com.impact.brain.auth.service;

import com.impact.brain.auth.dto.LoginRequest;
import com.impact.brain.configuration.security.user.UserDetailsImpl;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.service.user.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userServiceImpl;

    @Override
    public String login(LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        try{
            httpServletRequest.login(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            User authenticatedUser = userDetails.getUser();
            return authenticatedUser.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
