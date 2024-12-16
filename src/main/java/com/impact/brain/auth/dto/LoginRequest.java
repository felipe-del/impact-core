package com.impact.brain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El email no puede estar vacío")
    String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    String password;
}