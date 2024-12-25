package com.impact.core.module.auth.controller;

import com.impact.core.module.auth.payload.request.*;
import com.impact.core.module.auth.payload.response.JwtResponse;
import com.impact.core.module.auth.service.AuthService;
import com.impact.core.module.user.dto.UserDTO;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO userDTO = authService.register(registerRequest);

        return ResponseEntity.ok(ApiResponse.<UserDTO>builder()
                .message("Usuario registrado.")
                .data(userDTO)
                .build());
    }

    @GetMapping("/user-session")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserDTO>> getUserSession() {
        UserDTO dto = authService.getUserSession();

        return ResponseEntity.ok(ApiResponse.<UserDTO>builder()
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

    @PostMapping("/change-user-state")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<UserDTO>> changeUserState(@Valid @RequestBody ChangeUserStateRequest changeUserStateRequest) {
        UserDTO userDTO = authService.changeUserState(changeUserStateRequest);

        return ResponseEntity.ok(ApiResponse.<UserDTO>builder()
                .message("Estado del usuario cambiado a '" + userDTO.getStateName() + "'.")
                .data(userDTO)
                .build());
    }

    @PostMapping("/change-user-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<UserDTO>> changeUserRole(@Valid @RequestBody ChangeUserRoleRequest changeUserRoleRequest) {
        UserDTO userDTO = authService.changeUserRole(changeUserRoleRequest);

        return ResponseEntity.ok(ApiResponse.<UserDTO>builder()
                .message("Rol del usuario cambiado a '" + userDTO.getRoleName() + "'.")
                .data(userDTO)
                .build());
    }
}
