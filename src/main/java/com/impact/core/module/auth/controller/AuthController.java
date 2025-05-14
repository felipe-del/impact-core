package com.impact.core.module.auth.controller;

import com.impact.core.module.auth.payload.request.*;
import com.impact.core.module.auth.payload.response.JwtResponse;
import com.impact.core.module.auth.service.AuthService;
import com.impact.core.module.user.payload.request.UserRequest;
import com.impact.core.module.user.payload.response.UserResponse;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling authentication-related routes.
 * Provides endpoints for user login, registration, password management, and role/state changes.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Endpoint for logging in a user.
     *
     * @param loginRequest The login request containing user credentials.
     * @return A response entity containing the JWT token and a success message.
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<JwtResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);

        return ResponseEntity.ok(ResponseWrapper.<JwtResponse>builder()
                .message("Usuario logueado.")
                .data(jwtResponse)
                .build());
    }

    /**
     * Endpoint for registering a new user.
     *
     * @param registerRequest The registration request containing user information.
     * @return A response entity containing the created user information and a success message.
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<UserResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<UserResponse>builder()
                .message("Usuario registrado.")
                .data(userResponse)
                .build());
    }

    /**
     * Endpoint for saving a new user with a random password. Restricted to ADMINISTRATOR and MANAGER roles.
     *
     * @param newUserRequest The request containing new user information.
     * @param userDetailsSession The session details of the authenticated user making the request.
     * @return A response entity containing the saved user information and a success message.
     */
    @PostMapping("/save-user")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<UserResponse>> saveUser(
            @Valid @RequestBody NewUserRequest newUserRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetailsSession) {
        UserResponse userResponse = authService.saveUserWithRandomPassword(newUserRequest, userDetailsSession);

        return ResponseEntity.ok(ResponseWrapper.<UserResponse>builder()
                .message("Usuario guardado.")
                .data(userResponse)
                .build());
    }

    /**
     * Endpoint for fetching the current user session. Only accessible if authenticated.
     *
     * @return A response entity containing the current user's information.
     */
    @GetMapping("/user-session")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<UserResponse>> getUserSession() {
        UserResponse dto = authService.getUserSession();

        return ResponseEntity.ok(ResponseWrapper.<UserResponse>builder()
                .message("Usuario de la sesión obtenido.")
                .data(dto)
                .build());
    }

    /**
     * Endpoint for logging out a user.
     *
     * @param logoutRequest The request containing the JWT token to be invalidated.
     * @return A response entity with a success message indicating the user has been logged out.
     */
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<Void>> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Usuario deslogueado.")
                .build());
    }

    /**
     * Endpoint for initiating a password reset. Sends a recovery token to the user's email.
     *
     * @param forgotPasswordRequest The request containing the user's email for password recovery.
     * @return A response entity with a success message indicating the recovery email has been sent.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseWrapper<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.forgotPassword(forgotPasswordRequest);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Se ha enviado un correo con el token de recuperación.")
                .build());
    }

    /**
     * Endpoint for resetting the user's password using a recovery token.
     *
     * @param resetPasswordRequest The request containing the recovery token and new password.
     * @return A response entity with a success message indicating the password has been reset.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseWrapper<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Contraseña restablecida.")
                .build());
    }

    /**
     * Endpoint for changing the user's password. Only accessible if authenticated.
     *
     * @param changePasswordRequest The request containing the current and new passwords.
     * @return A response entity with a success message indicating the password has been changed.
     */
    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        authService.changePassword(changePasswordRequest);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Contraseña cambiada.")
                .build());
    }

    /**
     * Endpoint for changing the state of a user. Only accessible to ADMINISTRATOR roles.
     *
     * @param id The ID of the user whose state is to be changed.
     * @param changeUserStateRequest The request containing the new state for the user.
     * @param userDetails The session details of the authenticated user performing the action.
     * @return A response entity containing the updated user information and a success message.
     */
    @PostMapping("/change-user-state/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ResponseWrapper<UserResponse>> changeUserState(
            @PathVariable int id,
            @Valid @RequestBody ChangeUserStateRequest changeUserStateRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponse userResponse = authService.changeUserState(id, changeUserStateRequest, userDetails);

        return ResponseEntity.ok(ResponseWrapper.<UserResponse>builder()
                .message("Estado del usuario cambiado a '" + userResponse.getUserStateResponse().getStateName() + "'.")
                .data(userResponse)
                .build());
    }

    /**
     * Endpoint for changing the role of a user. Only accessible to ADMINISTRATOR roles.
     *
     * @param id The ID of the user whose role is to be changed.
     * @param changeUserRoleRequest The request containing the new role for the user.
     * @param userDetails The session details of the authenticated user performing the action.
     * @return A response entity containing the updated user information and a success message.
     */
    @PostMapping("/change-user-role/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ResponseWrapper<UserResponse>> changeUserRole(
            @PathVariable int id,
            @Valid @RequestBody ChangeUserRoleRequest changeUserRoleRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponse userResponse = authService.changeUserRole(id, changeUserRoleRequest, userDetails);

        return ResponseEntity.ok(ResponseWrapper.<UserResponse>builder()
                .message("Rol del usuario cambiado a '" + userResponse.getUserRoleResponse().getRoleName() + "'.")
                .data(userResponse)
                .build());
    }
}
