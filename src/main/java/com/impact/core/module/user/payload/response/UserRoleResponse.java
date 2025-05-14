package com.impact.core.module.user.payload.response;

import lombok.*;

/**
 * Data Transfer Object (DTO) that represents the user role in the system,
 * including its identifier, name, and description.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {

    /**
     * Unique identifier for the user role.
     */
    private Integer id;

    /**
     * The name of the role assigned to the user (e.g., "ADMIN", "USER").
     */
    private String roleName;

    /**
     * A brief description of the role and its permissions or responsibilities.
     */
    private String description;
}
