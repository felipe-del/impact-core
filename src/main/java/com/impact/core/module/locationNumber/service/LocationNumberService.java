package com.impact.core.module.locationNumber.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.locationNumber.entity.LocationNumber;
import com.impact.core.module.locationNumber.mapper.LocationNumberMapper;
import com.impact.core.module.locationNumber.payload.request.LocationNumberRequest;
import com.impact.core.module.locationNumber.payload.response.LocationNumberResponse;
import com.impact.core.module.locationNumber.repository.LocationNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("locationNumberService")
@RequiredArgsConstructor
public class LocationNumberService {
    public final LocationNumberRepository locationNumberRepository;
    public final LocationNumberMapper locationNumberMapper;

    public List<LocationNumberResponse> findAll() {
        return locationNumberRepository.findAll().stream()
                .map(locationNumberMapper::toDTO)
                .toList();
    }

    public LocationNumberResponse save(LocationNumberRequest locationNumberRequest) {
        LocationNumber locationNumber = locationNumberMapper.toEntity(locationNumberRequest);
        LocationNumber savedLocationNumber = locationNumberRepository.save(locationNumber);
        return locationNumberMapper.toDTO(savedLocationNumber);
    }

    public LocationNumberResponse update(int id, LocationNumberRequest locationNumberRequest) {
        LocationNumber locationNumber = this.findById(id);
        LocationNumber updatedLocationNumber = locationNumberMapper.toEntity(locationNumberRequest);
        updatedLocationNumber.setId(locationNumber.getId());
        LocationNumber savedLocationNumber = locationNumberRepository.save(updatedLocationNumber);
        return locationNumberMapper.toDTO(savedLocationNumber);
    }

    public LocationNumberResponse delete(int id) {
        LocationNumber locationNumber = this.findById(id);
        locationNumberRepository.delete(locationNumber);
        return locationNumberMapper.toDTO(locationNumber);
    }

    public LocationNumber findById(int id) {
        return locationNumberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El número: " + id + " de ubicación no existe."));
    }


}
