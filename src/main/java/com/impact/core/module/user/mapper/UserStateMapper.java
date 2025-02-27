package com.impact.core.module.user.mapper;

import com.impact.core.module.user.entity.UserState;
import com.impact.core.module.user.payload.response.UserStateResponse;
import org.springframework.stereotype.Component;

@Component
public class UserStateMapper {
    public UserStateResponse toDTO(UserState userState) {
        return UserStateResponse.builder()
                .id(userState.getId())
                .stateName(userState.getName().toString())
                .description(userState.getDescription())
                .build();
    }
}
