package com.impact.core.module.user.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object (DTO) for user data input during creation or update.
 * It contains validation annotations to ensure that required fields are provided.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    /**
     * The name of the user. This field is required and cannot be blank.
     */
    @NotBlank(message = "El nombre es requerido")
    String name;

    /**
     * The email of the user. This field is required and cannot be blank.
     */
    @NotBlank(message = "El email es requerido")
    String email;
}
