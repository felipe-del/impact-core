package com.impact.core.module.auth.controller;

import com.impact.core.module.user.entity.UserRole;
import com.impact.core.module.user.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRoleService userRoleService;

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public List<UserRole> allAccess() {
        return userRoleService.findAll();
    }

    @GetMapping("/manager-admin")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR')")
    public String userAccess() {
        return "MANAGER ADMINISTRATOR Content.";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public String moderatorAccess() {
        return "TEACHER Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String adminAccess() {
        return "ADMIN Board.";
    }
}

