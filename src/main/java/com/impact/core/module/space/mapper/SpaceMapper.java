package com.impact.core.module.space.mapper;

import com.impact.core.module.buildingLocation.mapper.BuildingLocationMapper;
import com.impact.core.module.buildingLocation.service.BuildingLocationService;
import com.impact.core.module.space.entity.Space;
import com.impact.core.module.space.payload.request.SpaceRequest;
import com.impact.core.module.space.payload.response.SpaceResponse;
import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import com.impact.core.module.spaceStatus.enun.ESpaceStatus;
import com.impact.core.module.spaceStatus.mapper.SpaceStatusMapper;
import com.impact.core.module.spaceStatus.service.SpaceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class SpaceMapper {

    public final BuildingLocationService buildingLocationService;
    public final SpaceStatusService spaceStatusService;
    public final BuildingLocationMapper buildingLocationMapper;
    public final SpaceStatusMapper spaceStatusMapper;

    public Space toEntity(SpaceRequest request) {
        return Space.builder()
                .name(request.getName())
                .spaceCode(request.getSpaceCode())
                .location(buildingLocationService.findById(request.getBuildingLocationId()))
                .maxPeople(request.getMaxPeople())
                .openTime(LocalTime.parse(request.getOpenTime()))
                .closeTime(LocalTime.parse(request.getCloseTime()))
                .status(getStatus(request.getSpaceStatusName()))
                .isDeleted(false)
                .build();
    }

    public SpaceResponse toDTO(Space space) {
        return SpaceResponse.builder()
                .id(space.getId())
                .name(space.getName())
                .spaceCode(space.getSpaceCode())
                .buildingLocationResponse(buildingLocationMapper.toDTO(space.getLocation()))
                .maxPeople(space.getMaxPeople())
                .openTime(space.getOpenTime().toString())
                .closeTime(space.getCloseTime().toString())
                .spaceStatus(spaceStatusMapper.toDTO(space.getStatus()))
                .build();
    }


    private SpaceStatus getStatus(String name) {
        return switch (name.toLowerCase()) {
            case "loaned" -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_LOANED);
            case "in maintenance" -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_IN_MAINTENANCE);
            case "out of service" -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_OUT_OF_SERVICE);
            default -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_AVAILABLE);
        };
    }

}
