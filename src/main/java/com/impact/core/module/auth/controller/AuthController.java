package com.impact.core.module.auth.controller;

import com.impact.core.module.auth.payload.request.*;
import com.impact.core.module.auth.payload.response.JwtResponse;
import com.impact.core.module.auth.service.AuthService;
import com.impact.core.module.user.payload.response.UserResponse;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.<JwtResponse>builder()
                .message("Usuario logueado.")
                .data(jwtResponse)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<UserResponse>builder()
                .message("Usuario registrado.")
                .data(userResponse)
                .build());
    }

    @GetMapping("/user-session")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserResponse>> getUserSession() {
        UserResponse dto = authService.getUserSession();

        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .message("Usuario de la sesión obtenido.")
                .data(dto)
                .build());
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Usuario deslogueado.")
                .build());
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.forgotPassword(forgotPasswordRequest);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Se ha enviado un correo con el token de recuperación.")
                .build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Contraseña restablecida.")
                .build());
    }

    @PostMapping("/change-user-state/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<UserResponse>> changeUserState(@PathVariable int id, @Valid @RequestBody ChangeUserStateRequest changeUserStateRequest) {
        UserResponse userResponse = authService.changeUserState(id, changeUserStateRequest);

        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .message("Estado del usuario cambiado a '" + userResponse.getStateName() + "'.")
                .data(userResponse)
                .build());
    }

    @PostMapping("/change-user-role/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<UserResponse>> changeUserRole(@PathVariable int id, @Valid @RequestBody ChangeUserRoleRequest changeUserRoleRequest) {
        UserResponse userResponse = authService.changeUserRole(id, changeUserRoleRequest);

        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .message("Rol del usuario cambiado a '" + userResponse.getRoleName() + "'.")
                .data(userResponse)
                .build());
    }
}
