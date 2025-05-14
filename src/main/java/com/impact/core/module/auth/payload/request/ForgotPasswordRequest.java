package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for initiating the password recovery process.
 * <p>
 * This Data Transfer Object (DTO) contains the email address of the user who requested to reset their password.
 * It is typically submitted to an endpoint for sending a password reset email.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {

    /**
     * The email address of the user requesting a password reset.
     * Must not be blank and must be a valid email format.
     */
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    String email;
}
