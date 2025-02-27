package com.impact.core.module.buildingLocation.mapper;

import com.impact.core.module.buildingLocation.entity.BuildingLocation;
import com.impact.core.module.buildingLocation.payload.request.BuildingLocationRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingLocationResponse;
import com.impact.core.module.buildingLocation.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildingLocationMapper {

    public final BuildingService buildingService;
    public final BuildingMapper buildingMapper;

    public BuildingLocation toEntity(BuildingLocationRequest buildingLocationRequest) {
        return BuildingLocation.builder()
                .building(buildingService.findById(buildingLocationRequest.getBuildingId()))
                .floor(buildingLocationRequest.getFloor())
                .build();
    }

    public BuildingLocationResponse toDTO(BuildingLocation buildingLocation) {
        return BuildingLocationResponse.builder()
                .id(buildingLocation.getId())
                .building(buildingMapper.toDTO(buildingLocation.getBuilding()))
                .floor(buildingLocation.getFloor())
                .build();
    }
}
