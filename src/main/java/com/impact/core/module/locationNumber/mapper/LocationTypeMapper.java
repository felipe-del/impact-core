package com.impact.core.module.locationNumber.mapper;

import com.impact.core.module.locationNumber.entity.LocationType;
import com.impact.core.module.locationNumber.payload.request.LocationTypeRequest;
import com.impact.core.module.locationNumber.payload.response.LocationTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link LocationType} entities and their corresponding DTOs
 * ({@link LocationTypeRequest} and {@link LocationTypeResponse}).
 * <p>
 * This class provides methods to map {@link LocationTypeRequest} objects to {@link LocationType} entities
 * and {@link LocationType} entities to {@link LocationTypeResponse} DTOs.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class LocationTypeMapper {

    /**
     * Converts a {@link LocationTypeRequest} to a {@link LocationType} entity.
     *
     * @param locationTypeRequest the DTO object containing the data to be converted to an entity.
     * @return a {@link LocationType} entity populated with the data from the request.
     */
    public LocationType toEntity(LocationTypeRequest locationTypeRequest) {
        return LocationType.builder()
                .typeName(locationTypeRequest.getTypeName())
                .build();
    }

    /**
     * Converts a {@link LocationType} entity to a {@link LocationTypeResponse} DTO.
     *
     * @param locationType the entity to be converted to a response DTO.
     * @return a {@link LocationTypeResponse} object representing the given entity.
     */
    public LocationTypeResponse toDTO(LocationType locationType) {
        return LocationTypeResponse.builder()
                .id(locationType.getId())
                .typeName(locationType.getTypeName())
                .build();
    }
}
