package com.impact.core.module.locationNumber.mapper;

import com.impact.core.module.locationNumber.entity.LocationType;
import com.impact.core.module.locationNumber.payload.request.LocationTypeRequest;
import com.impact.core.module.locationNumber.payload.response.LocationTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationTypeMapper {

    public LocationType toEntity(LocationTypeRequest locationTypeRequest) {
        return LocationType.builder()
                .typeName(locationTypeRequest.getTypeName())
                .build();
    }

    public LocationTypeResponse toDTO(LocationType locationType) {
        return LocationTypeResponse.builder()
                .id(locationType.getId())
                .typeName(locationType.getTypeName())
                .build();
    }
}
