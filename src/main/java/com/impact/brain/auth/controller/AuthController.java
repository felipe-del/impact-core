package com.impact.brain.auth.controller;

import com.impact.brain.auth.dto.LoginRequest;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.service.UserService;
import com.impact.brain.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public String auth(@RequestBody LoginRequest request) {
        return userServiceImpl.findByEmail(request.getUsername()).getName();
    }
}
