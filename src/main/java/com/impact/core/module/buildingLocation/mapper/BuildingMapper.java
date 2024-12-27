package com.impact.core.module.buildingLocation.mapper;

import com.impact.core.module.buildingLocation.entity.Building;
import com.impact.core.module.buildingLocation.payload.request.BuildingRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingResponse;
import org.springframework.stereotype.Component;

@Component
public class BuildingMapper {

    public Building toEntity(BuildingRequest buildingRequest) {
        return Building.builder()
                .name(buildingRequest.getName())
                .build();
    }

    public BuildingResponse toDTO(Building building) {
        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .build();
    }
}
