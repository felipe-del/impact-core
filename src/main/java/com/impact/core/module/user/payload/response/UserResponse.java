package com.impact.core.module.user.payload.response;

import lombok.*;

/**
 * Data Transfer Object (DTO) used to return essential user information
 * across different layers of the application.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    /**
     * Unique identifier for the user.
     */
    private Integer id;

    /**
     * Full name of the user.
     */
    private String name;


    /**
     * Email address associated with the user.
     */
    private String email;

    /**
     * Role assigned to the user, encapsulated in a {@link UserRoleResponse}
     * Data Transfer Object (DTO). This determines the user's permissions
     * and access level within the system.
     */
    private UserRoleResponse userRoleResponse;

    /**
     * Current state of the user, encapsulated in a {@link UserStateResponse}
     * Data Transfer Object (DTO). Reflects whether the user is active,
     * inactive, or suspended in the system.
     */
    private UserStateResponse userStateResponse;
}