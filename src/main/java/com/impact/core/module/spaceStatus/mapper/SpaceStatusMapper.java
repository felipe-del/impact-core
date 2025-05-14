package com.impact.core.module.spaceStatus.mapper;

import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting a {@link SpaceStatus} entity to its corresponding {@link SpaceStatusResponse} Data Transfer Object (DTO).
 * <p>
 * The purpose of this mapper is to transform entity objects into DTOs that can be used in the response layer for client communication.
 */
@Component
@RequiredArgsConstructor
public class SpaceStatusMapper {

    /**
     * Converts a {@link SpaceStatus} entity to a {@link SpaceStatusResponse} DTO.
     * <p>
     * This method is used to transform a {@link SpaceStatus} entity into a format that can be returned to the client as part of the response.
     *
     * @param spaceStatus the {@link SpaceStatus} entity to be converted
     * @return a {@link SpaceStatusResponse} DTO representing the entity
     */
    public SpaceStatusResponse toDTO(SpaceStatus spaceStatus) {
        return SpaceStatusResponse.builder()
                .id(spaceStatus.getId())
                .name(spaceStatus.getName().toString())
                .description(spaceStatus.getDescription())
                .build();
    }
}
