package com.impact.core.module.user.controller;

import com.impact.core.module.user.payload.response.UserRoleResponse;
import com.impact.core.module.user.service.UserRoleService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller responsible for exposing endpoints related to user roles.
 * <p>
 * The {@link UserRoleController} handles the retrieval of all user roles stored in the system.
 */
@RestController
@RequestMapping("/api/user-role")
@RequiredArgsConstructor
public class UserRoleController {
    public final UserRoleService userRoleService;

    /**
     * Retrieves all user roles in the system.
     * <p>
     * This endpoint is accessible only to users with the role 'ADMINISTRATOR' or 'MANAGER'.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link UserRoleResponse} objects
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<UserRoleResponse>>> getAllUserRoles() {
        List<UserRoleResponse> userRoleResponses = userRoleService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<UserRoleResponse>>builder()
                .message("Lista de roles de usuario.")
                .data(userRoleResponses)
                .build());
    }
}
