package com.impact.core.module.user.mapper;

import com.impact.core.module.user.entity.UserRole;
import com.impact.core.module.user.payload.response.UserRoleResponse;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper {
    public UserRoleResponse toDTO(UserRole userRole) {
        return UserRoleResponse.builder()
                .id(userRole.getId())
                .roleName(userRole.getName().toString())
                .description(userRole.getDescription())
                .build();
    }
}
