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
public class VerifyTokenRequest {
    @NotBlank(message = "El token no puede estar vacío")
    @Size(min = 6, max = 6, message = "El token debe tener 6 caracteres")
    private String token;
}
