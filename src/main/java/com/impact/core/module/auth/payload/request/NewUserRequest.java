package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50)
    @Email(message = "El email no es válido")
    private String email;

    @NotNull(message = "El rol del usuario no puede estar vacío")
    private int userRoleId;

    @NotNull(message = "El status del usuario no puede estar vacío")
    private int userStatusId;
}
