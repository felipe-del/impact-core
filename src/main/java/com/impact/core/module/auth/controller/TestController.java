package com.impact.core.module.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication-related test routes.
 * Provides endpoints for different user access levels.
 */
@RestController
@RequestMapping("/api/test/auth")
@RequiredArgsConstructor
public class TestController {

    /**
     * Public endpoint for all users.
     *
     * @return A message indicating public content.
     */
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    /**
     * Endpoint for users with the roles 'MANAGER' or 'ADMINISTRATOR'.
     *
     * @return A message indicating content for manager and administrator roles.
     */
    @GetMapping("/manager-admin")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR')")
    public String userAccess() {
        return "MANAGER ADMINISTRATOR Content.";
    }

    /**
     * Endpoint for users with the 'TEACHER' role.
     *
     * @return A message indicating content for the teacher role.
     */
    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public String moderatorAccess() {
        return "TEACHER Board.";
    }

    /**
     * Endpoint for users with the 'ADMINISTRATOR' role.
     *
     * @return A message indicating content for the administrator role.
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String adminAccess() {
        return "ADMIN Board.";
    }
}

