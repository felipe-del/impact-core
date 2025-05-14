package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for creating a new user.
 * <p>
 * This Data Transfer Object (DTO) contains the user's name, email, role ID, and status ID.
 * It is typically used in an endpoint where administrators can create a new user account.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {

    /**
     * The name of the user being created.
     * Must not be blank and must have a length between 3 and 30 characters.
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 30)
    private String name;

    /**
     * The email address of the user being created.
     * Must not be blank, must be a valid email format, and have a maximum length of 50 characters.
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50)
    @Email(message = "El email no es válido")
    private String email;

    /**
     * The ID of the role assigned to the user.
     * Must not be {@code null}.
     */
    @NotNull(message = "El rol del usuario no puede estar vacío")
    private int userRoleId;

    /**
     * The ID of the status assigned to the user.
     * Must not be {@code null}.
     */
    @NotNull(message = "El status del usuario no puede estar vacío")
    private int userStatusId;
}
