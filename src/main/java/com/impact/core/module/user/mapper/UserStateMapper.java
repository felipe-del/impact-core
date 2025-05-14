package com.impact.core.module.user.mapper;

import com.impact.core.module.user.entity.UserState;
import com.impact.core.module.user.payload.response.UserStateResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper component responsible for converting a UserState entity into a UserStateResponse Data Transfer Object (DTO).
 * This class handles the transformation of the UserState entity into its corresponding DTO, which is used in the application responses.
 */
@Component
public class UserStateMapper {

   /**
    * Converts a {@link UserState} entity into a UserStateResponse Data Transfer Object (DTO).
    *
    * @param userState The {@link UserState} entity to be converted.
    * @return The corresponding {@code UserStateResponse} DTO.
    *
    *
    */
    public UserStateResponse toDTO(UserState userState) {
        return UserStateResponse.builder()
                .id(userState.getId())
                .stateName(userState.getName().toString())
                .description(userState.getDescription())
                .build();
    }
}
