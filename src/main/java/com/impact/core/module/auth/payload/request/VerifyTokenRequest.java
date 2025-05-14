package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for verifying a token.
 * <p>
 * This Data Transfer Object (DTO) contains the token to be verified.
 * It is typically used in an endpoint where a user can verify a token, such as in password reset or email verification processes.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTokenRequest {

    /**
     * The token that needs to be verified.
     * Must not be blank and must be exactly 6 characters long.
     */
    @NotBlank(message = "El token no puede estar vacío")
    @Size(min = 6, max = 6, message = "El token debe tener 6 caracteres")
    private String token;
}
