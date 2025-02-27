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

@RestController
@RequestMapping("/api/user-role")
@RequiredArgsConstructor
public class UserRoleController {
    public final UserRoleService userRoleService;

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
