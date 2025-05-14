package com.impact.core.module.user.payload.response;

import lombok.*;

/**
 * Data Transfer Object (DTO) that represents the user state in the system,
 * including its identifier, name, and description.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStateResponse {

    /**
     * Unique identifier for the user state.
     */
    private Integer id;

    /**
     * The name of the user state (e.g., "ACTIVE", "INACTIVE", "SUSPENDED").
     */
    private String stateName;

    /**
     * A brief description of the user state, outlining its significance.
     */
    private String description;
}
