package com.impact.core.module.locationNumber.mapper;

import com.impact.core.module.locationNumber.entity.LocationNumber;
import com.impact.core.module.locationNumber.entity.LocationType;
import com.impact.core.module.locationNumber.payload.request.LocationNumberRequest;
import com.impact.core.module.locationNumber.payload.response.LocationNumberResponse;
import com.impact.core.module.locationNumber.service.LocationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationNumberMapper {
    public final LocationTypeService locationTypeService;

    public LocationNumber toEntity(LocationNumberRequest locationNumberRequest) {
        int locationTypeId = locationNumberRequest.getLocationTypeId();
        LocationType locationType = locationTypeService.findById(locationTypeId);

        return LocationNumber.builder()
                .locationNumber(locationNumberRequest.getLocationNumber())
                .locationType(locationType)
                .build();
    }

    public LocationNumberResponse toDTO(LocationNumber locationNumber) {
        return LocationNumberResponse.builder()
                .id(locationNumber.getId())
                .locationTypeName(locationNumber.getLocationType().getTypeName())
                .locationNumber(locationNumber.getLocationNumber())
                .build();
    }
}
