package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {
    @NotBlank(message = "El token no puede estar vacío")
    private String jwtToken;
}
