package com.impact.core.module.user.mapper;

import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyUserMapper { // I CALL IT MY USER MAPPER BECAUSE I HAVE A USER MAPPER IN THE SAME PACKAGE
    public final UserRoleMapper userRoleMapper;
    public final UserStateMapper userStateMapper;

    public UserResponse toDTO(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roleName(userRoleMapper.toDTO(user.getRole()))
                .stateName(userStateMapper.toDTO(user.getState()))
                .build();
    }
}
