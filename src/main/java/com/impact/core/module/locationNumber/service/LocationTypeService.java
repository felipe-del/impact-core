package com.impact.core.module.locationNumber.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.locationNumber.entity.LocationType;
import com.impact.core.module.locationNumber.mapper.LocationTypeMapper;
import com.impact.core.module.locationNumber.payload.request.LocationTypeRequest;
import com.impact.core.module.locationNumber.payload.response.LocationTypeResponse;
import com.impact.core.module.locationNumber.repository.LocationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("locationTypeService")
@RequiredArgsConstructor
public class LocationTypeService {
    public final LocationTypeRepository locationTypeRepository;
    public final LocationTypeMapper locationTypeMapper;

    public LocationTypeResponse save(LocationTypeRequest locationTypeRequest) {
        LocationType locationType = locationTypeMapper.toEntity(locationTypeRequest);
        LocationType savedLocationType = locationTypeRepository.save(locationType);

        return locationTypeMapper.toDTO(savedLocationType);
    }

    public LocationTypeResponse update(Integer id, LocationTypeRequest locationTypeRequest) {
        LocationType locationType = findById(id);
        locationType.setTypeName(locationTypeRequest.getTypeName());
        LocationType updatedLocationType = locationTypeRepository.save(locationType);

        return locationTypeMapper.toDTO(updatedLocationType);
    }

    public LocationTypeResponse delete(Integer id) {
        LocationType locationType = findById(id);
        locationTypeRepository.delete(locationType);

        return locationTypeMapper.toDTO(locationType);
    }

    public LocationType findById(Integer id) {
        return locationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La locationType con id " + id + " no existe"));
    }

    public List<LocationTypeResponse> findAll() {
        return locationTypeRepository.findAll().stream()
                .map(locationTypeMapper::toDTO)
                .toList();
    }
}
