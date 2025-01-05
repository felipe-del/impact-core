package com.impact.core.module.user.controller;

import com.impact.core.module.user.payload.request.UserRequest;
import com.impact.core.module.user.payload.response.UserResponse;
import com.impact.core.module.user.service.UserService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<UserResponse>>> getAllUsers() {
        List<UserResponse> userResponses = userService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<UserResponse>>builder()
                .message("Lista de usuarios.")
                .data(userResponses)
                .build());
    }

    // SAVE IS IMPLEMENTED^IN AUTHENTICATION CONTROLLER

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<UserResponse>> updateUser(@PathVariable int id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.update(id, userRequest);

        return ResponseEntity.ok(ResponseWrapper.<UserResponse>builder()
                .message("Usuario actualizado.")
                .data(userResponse)
                .build());
    }

    // DELETE... ITS NECESSARY?
}
