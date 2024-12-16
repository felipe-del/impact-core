package com.impact.brain.auth.controller;

import com.impact.brain.auth.dto.LoginRequest;
import com.impact.brain.auth.dto.LogoutRequest;
import com.impact.brain.auth.service.AuthServiceImpl;
import com.impact.brain.auth.service.JwtServiceImpl;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.service.user.UserServiceImpl;
import com.impact.brain.user.service.userJwtToken.UserJwtTokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;
    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtService;

    @PostMapping("/login")
    public String auth(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        return jwtService.generateToken(user);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest logoutRequest) {
        jwtService.blacklistToken(logoutRequest.getJwtToken());
    }

}
