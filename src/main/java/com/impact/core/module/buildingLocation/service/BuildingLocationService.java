package com.impact.core.module.buildingLocation.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.buildingLocation.entity.BuildingLocation;
import com.impact.core.module.buildingLocation.mapper.BuildingLocationMapper;
import com.impact.core.module.buildingLocation.payload.request.BuildingLocationRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingLocationResponse;
import com.impact.core.module.buildingLocation.repository.BuildingLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("buildingLocationService")
@RequiredArgsConstructor
public class BuildingLocationService {
    public final BuildingLocationRepository buildingLocationRepository;
    public final BuildingLocationMapper buildingLocationMapper;

    public BuildingLocationResponse save(BuildingLocationRequest buildingLocationRequest) {
        BuildingLocation buildingLocation = buildingLocationMapper.toEntity(buildingLocationRequest);
        BuildingLocation savedBuildingLocation = buildingLocationRepository.save(buildingLocation);
        return buildingLocationMapper.toDTO(savedBuildingLocation);
    }

    public BuildingLocationResponse update(Integer id, BuildingLocationRequest buildingLocationRequest) {
        BuildingLocation existingBuildingLocation = this.findById(id);
        BuildingLocation updatedBuildingLocation = buildingLocationMapper.toEntity(buildingLocationRequest);
        updatedBuildingLocation.setId(existingBuildingLocation.getId());
        BuildingLocation savedBuildingLocation = buildingLocationRepository.save(updatedBuildingLocation);
        return buildingLocationMapper.toDTO(savedBuildingLocation);
    }

    public BuildingLocationResponse delete(Integer id) {
        BuildingLocation buildingLocation = this.findById(id);
        buildingLocationRepository.delete(buildingLocation);
        return buildingLocationMapper.toDTO(buildingLocation);
    }

    public BuildingLocation findById(int id) {
        return buildingLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La ubicación del edificio con el id : " + id + " no se existe."));
    }

    public List<BuildingLocationResponse> findAll() {
        return buildingLocationRepository.findAll()
                .stream()
                .map(buildingLocationMapper::toDTO)
                .toList();
    }

}
