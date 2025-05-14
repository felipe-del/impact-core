package com.impact.core.module.buildingLocation.mapper;

import com.impact.core.module.buildingLocation.entity.BuildingLocation;
import com.impact.core.module.buildingLocation.payload.request.BuildingLocationRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingLocationResponse;
import com.impact.core.module.buildingLocation.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link BuildingLocation} entities and their corresponding
 * Data Transfer Objects (DTOs), such as {@link BuildingLocationRequest} and {@link BuildingLocationResponse}.
 * <p>
 * This component provides methods for converting from a request DTO to an entity and
 * from an entity to a response DTO. It uses {@link BuildingService} to resolve building references
 * and {@link BuildingMapper} to convert building entities to their respective DTOs.
 */
@Component
@RequiredArgsConstructor
public class BuildingLocationMapper {

    public final BuildingService buildingService;
    public final BuildingMapper buildingMapper;

    /**
     * Converts a {@link BuildingLocationRequest} Data Transfer Object (DTO)
     * to a {@link BuildingLocation} entity.
     *
     * @param buildingLocationRequest the input request DTO
     * @return the resulting entity
     */
    public BuildingLocation toEntity(BuildingLocationRequest buildingLocationRequest) {
        return BuildingLocation.builder()
                .building(buildingService.findById(buildingLocationRequest.getBuildingId()))
                .floor(buildingLocationRequest.getFloor())
                .build();
    }

    /**
     * Converts a {@link BuildingLocation} entity to a {@link BuildingLocationResponse}
     * Data Transfer Object (DTO).
     *
     * @param buildingLocation the entity to convert
     * @return the resulting response DTO
     */
    public BuildingLocationResponse toDTO(BuildingLocation buildingLocation) {
        return BuildingLocationResponse.builder()
                .id(buildingLocation.getId())
                .building(buildingMapper.toDTO(buildingLocation.getBuilding()))
                .floor(buildingLocation.getFloor())
                .build();
    }
}
