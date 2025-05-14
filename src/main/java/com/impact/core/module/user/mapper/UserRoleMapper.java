package com.impact.core.module.user.mapper;

import com.impact.core.module.user.entity.UserRole;
import com.impact.core.module.user.payload.response.UserRoleResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper component responsible for converting a UserRole entity into a UserRoleResponse Data Transfer Object (DTO).
 * This class handles the transformation of the UserRole entity into its corresponding DTO, which is used in the application responses.
 */
@Component
public class UserRoleMapper {

    /**
     * Converts a {@link UserRole} entity into a {@link UserRoleResponse} Data Transfer Object (DTO).
     *
     * @param userRole The UserRole entity to be converted.
     * @return The corresponding UserRoleResponse DTO.
     */
    public UserRoleResponse toDTO(UserRole userRole) {
        return UserRoleResponse.builder()
                .id(userRole.getId())
                .roleName(userRole.getName().toString()) // Converts the name of the UserRole enum to a string
                .description(userRole.getDescription())
                .build();
    }
}
