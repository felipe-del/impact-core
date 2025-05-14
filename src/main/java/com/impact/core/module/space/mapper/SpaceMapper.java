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

/**
 * Mapper class for converting between {@link Space} entities and their corresponding
 * request and response Data Transfer Objects (DTOs), such as {@link SpaceRequest} and {@link SpaceResponse}.
 * <p>
 * This class is responsible for handling transformations during space-related operations.
 */
@Component
@RequiredArgsConstructor
public class SpaceMapper {

    public final BuildingLocationService buildingLocationService;
    public final SpaceStatusService spaceStatusService;
    public final BuildingLocationMapper buildingLocationMapper;
    public final SpaceStatusMapper spaceStatusMapper;

    /**
     * Converts a {@link SpaceRequest} Data Transfer Object (DTO) into a {@link Space} entity.
     *
     * @param request the {@link SpaceRequest} containing input data
     * @return a populated {@link Space} entity
     */
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

    /**
     * Converts a {@link Space} entity into a {@link SpaceResponse} Data Transfer Object (DTO).
     *
     * @param space the {@link Space} entity to convert
     * @return the resulting {@link SpaceResponse}
     */
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

    /**
     * Retrieves a {@link SpaceStatus} entity based on the provided status name.
     * The name is mapped to a corresponding enumeration value from {@link ESpaceStatus}.
     *
     * @param name the name of the space status
     * @return the corresponding {@link SpaceStatus} entity
     */
    private SpaceStatus getStatus(String name) {
        return switch (name.toLowerCase()) {
            case "loaned" -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_LOANED);
            case "in_maintenance" -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_IN_MAINTENANCE);
            case "out_of_service" -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_OUT_OF_SERVICE);
            case "earring" -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_EARRING);
            default -> spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_AVAILABLE);
        };
    }

}
