package com.impact.core.module.auth.controller;

import com.impact.core.module.auth.payload.request.*;
import com.impact.core.module.auth.payload.response.JwtResponse;
import com.impact.core.module.auth.service.AuthService;
import com.impact.core.module.user.payload.response.UserResponse;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<JwtResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);

        return ResponseEntity.ok(ResponseWrapper.<JwtResponse>builder()
                .message("Usuario logueado.")
                .data(jwtResponse)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<UserResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<UserResponse>builder()
                .message("Usuario registrado.")
                .data(userResponse)
                .build());
    }

    @GetMapping("/user-session")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<UserResponse>> getUserSession() {
        UserResponse dto = authService.getUserSession();

        return ResponseEntity.ok(ResponseWrapper.<UserResponse>builder()
                .message("Usuario de la sesión obtenido.")
                .data(dto)
                .build());
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<Void>> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Usuario deslogueado.")
                .build());
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<ResponseWrapper<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.forgotPassword(forgotPasswordRequest);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Se ha enviado un correo con el token de recuperación.")
                .build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseWrapper<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Contraseña restablecida.")
                .build());
    }

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
