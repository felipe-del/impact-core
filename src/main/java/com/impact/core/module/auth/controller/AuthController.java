package com.impact.core.module.auth.controller;

import com.impact.core.module.auth.payload.request.LoginRequest;
import com.impact.core.module.auth.payload.request.LogoutRequest;
import com.impact.core.module.auth.payload.request.RegisterRequest;
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

    @GetMapping("/getUserSession")
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
}
