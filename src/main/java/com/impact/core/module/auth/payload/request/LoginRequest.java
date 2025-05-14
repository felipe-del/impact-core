package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for user authentication during the login process.
 * <p>
 * This Data Transfer Object (DTO) contains the email and password of the user attempting to log in.
 * It is typically submitted to an authentication endpoint for validating the user's credentials.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * The email address of the user attempting to log in.
     * Must be a valid email format and cannot be blank.
     */
    @Email(message = "El email no es válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    /**
     * The password of the user attempting to log in.
     * Cannot be blank.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}
