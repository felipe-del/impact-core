package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "El token verificado no puede estar vacío")
    private String token;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
    private String password;
}
