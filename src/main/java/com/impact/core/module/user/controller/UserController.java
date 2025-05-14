package com.impact.core.module.user.controller;

import com.impact.core.module.user.payload.request.UserRequest;
import com.impact.core.module.user.payload.response.UserResponse;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user-related operations, such as retrieving, updating, and saving users.
 * The {@link UserController} provides endpoints for administrators and managers to access and manage users.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    /**
     * Retrieves all users in the system. Accessible by users with the role of 'ADMINISTRATOR' or 'MANAGER'.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link UserResponse} objects
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<UserResponse>>> getAllUsers() {
        List<UserResponse> userResponses = userService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<UserResponse>>builder()
                .message("Lista de usuarios.")
                .data(userResponses)
                .build());
    }

    /**
     * Updates an existing user by their ID. Accessible by users with the role of 'ADMINISTRATOR' or 'MANAGER'.
     *
     * @param id The ID of the user to be updated
     * @param userRequest The user data to update, wrapped in a {@link UserRequest} object
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the updated {@link UserResponse} object
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<UserResponse>> updateUser(@PathVariable int id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.update(id, userRequest);

        return ResponseEntity.ok(ResponseWrapper.<UserResponse>builder()
                .message("Usuario actualizado.")
                .data(userResponse)
                .build());
    }
}
