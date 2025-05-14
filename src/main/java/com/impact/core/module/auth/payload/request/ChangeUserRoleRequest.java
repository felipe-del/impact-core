package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for changing the role of a user.
 * <p>
 * This Data Transfer Object (DTO) contains the identifier of the role that will be assigned to the user.
 * It is typically used in an endpoint that allows administrators to modify user roles.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserRoleRequest {

    /**
     * The ID of the role to be assigned to the user.
     * Must not be {@code null}.
     */
    @NotNull(message = "El role del usuario no puede estar vacío")
    private int roleId;
}
