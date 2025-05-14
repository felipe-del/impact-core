package com.impact.core.module.buildingLocation.mapper;

import com.impact.core.module.buildingLocation.entity.Building;
import com.impact.core.module.buildingLocation.payload.request.BuildingRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingResponse;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link Building} entities and their corresponding
 * Data Transfer Objects (DTOs), such as {@link BuildingRequest} and {@link BuildingResponse}.
 * <p>
 * This component provides methods for converting from a request DTO to an entity and
 * from an entity to a response DTO.
 */
@Component
public class BuildingMapper {

    /**
     * Converts a {@link BuildingRequest} Data Transfer Object (DTO)
     * to a {@link Building} entity.
     *
     * @param buildingRequest the input request DTO
     * @return the resulting entity
     */
    public Building toEntity(BuildingRequest buildingRequest) {
        return Building.builder()
                .name(buildingRequest.getName())
                .build();
    }

    /**
     * Converts a {@link Building} entity to a {@link BuildingResponse}
     * Data Transfer Object (DTO).
     *
     * @param building the entity to convert
     * @return the resulting response DTO
     */
    public BuildingResponse toDTO(Building building) {
        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .build();
    }
}
