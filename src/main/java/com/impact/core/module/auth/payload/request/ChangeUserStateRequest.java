package com.impact.core.module.auth.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class used for changing the state of a user.
 * <p>
 * This Data Transfer Object (DTO) contains the identifier of the state that will be assigned to the user.
 * It is typically used in an endpoint that allows administrators to modify user states.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserStateRequest {

    /**
     * The ID of the state to be assigned to the user.
     * Must not be {@code null}.
     */
    @NotNull(message = "El estado del usuario no puede estar vacío")
    private int stateId;
}
