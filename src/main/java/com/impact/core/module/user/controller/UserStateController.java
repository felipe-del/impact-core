package com.impact.core.module.user.controller;

import com.impact.core.module.user.payload.response.UserStateResponse;
import com.impact.core.module.user.service.UserStateService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user-state")
@RequiredArgsConstructor
public class UserStateController {
    public final UserStateService userStateService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<UserStateResponse>>> getAllUserStates() {
        List<UserStateResponse> userStateResponses = userStateService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<UserStateResponse>>builder()
                .message("Lista de estados de usuario.")
                .data(userStateResponses)
                .build());
    }
}
