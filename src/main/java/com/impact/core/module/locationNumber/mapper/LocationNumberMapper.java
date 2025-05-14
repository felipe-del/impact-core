package com.impact.core.module.locationNumber.mapper;

import com.impact.core.module.locationNumber.entity.LocationNumber;
import com.impact.core.module.locationNumber.entity.LocationType;
import com.impact.core.module.locationNumber.payload.request.LocationNumberRequest;
import com.impact.core.module.locationNumber.payload.response.LocationNumberResponse;
import com.impact.core.module.locationNumber.service.LocationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link LocationNumber} entities and their corresponding DTOs
 * ({@link LocationNumberRequest} and {@link LocationNumberResponse}).
 * <p>
 * This class provides methods to map {@link LocationNumberRequest} objects to {@link LocationNumber}
 * entities and {@link LocationNumber} entities to {@link LocationNumberResponse} DTOs.
 * It also utilizes the {@link LocationTypeService} to map location type IDs to corresponding {@link LocationType} entities.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class LocationNumberMapper {
    public final LocationTypeService locationTypeService;

    /**
     * Converts a {@link LocationNumberRequest} to a {@link LocationNumber} entity.
     *
     * @param locationNumberRequest the DTO object containing the data to be converted to an entity.
     * @return a {@link LocationNumber} entity populated with the data from the request.
     */
    public LocationNumber toEntity(LocationNumberRequest locationNumberRequest) {
        int locationTypeId = locationNumberRequest.getLocationTypeId();
        LocationType locationType = locationTypeService.findById(locationTypeId);

        return LocationNumber.builder()
                .locationNumber(locationNumberRequest.getLocationNumber())
                .locationType(locationType)
                .build();
    }

    /**
     * Converts a {@link LocationNumber} entity to a {@link LocationNumberResponse} DTO.
     *
     * @param locationNumber the entity to be converted to a response DTO.
     * @return a {@link LocationNumberResponse} object representing the given entity.
     */
    public LocationNumberResponse toDTO(LocationNumber locationNumber) {
        return LocationNumberResponse.builder()
                .id(locationNumber.getId())
                .locationTypeName(locationNumber.getLocationType().getTypeName())
                .locationNumber(locationNumber.getLocationNumber())
                .build();
    }
}
