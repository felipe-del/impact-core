package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Request class used for registering a new user.
 * <p>
 * This Data Transfer Object (DTO) contains the user's name, email, and password.
 * It is typically used in an endpoint for user registration.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class RegisterRequest {

    /**
     * The name of the user being registered.
     * Must not be blank and must have a length between 3 and 20 characters.
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 20)
    private String name;

    /**
     * The email address of the user being registered.
     * Must not be blank, must be a valid email format, and have a maximum length of 50 characters.
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50)
    @Email(message = "El email no es válido")
    private String email;

    /**
     * The password of the user being registered.
     * Must not be blank and must have a length between 6 and 40 characters.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
    private String password;
}
