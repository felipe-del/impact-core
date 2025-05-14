package com.impact.core.module.user.mapper;

import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper component responsible for converting a {@link User} entity into a User Data Transfer Object (DTO).
 * This mapper handles the transformation of related entities such as {@code UserRole} and {@code UserState} into their
 * respective DTOs for use in the application's responses.
 */
@Component
@RequiredArgsConstructor
public class MyUserMapper {
    public final UserRoleMapper userRoleMapper;
    public final UserStateMapper userStateMapper;

    /**
     * Converts a User entity into a UserResponse Data Transfer Object (DTO).
     *
     * @param user The User entity to be converted.
     * @return The corresponding UserResponse DTO.
     */
    public UserResponse toDTO(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userRoleResponse(userRoleMapper.toDTO(user.getRole()))
                .userStateResponse(userStateMapper.toDTO(user.getState()))
                .build();
    }
}
