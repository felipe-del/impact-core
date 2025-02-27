package com.impact.core.module.buildingLocation.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.buildingLocation.entity.Building;
import com.impact.core.module.buildingLocation.mapper.BuildingMapper;
import com.impact.core.module.buildingLocation.payload.request.BuildingRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingResponse;
import com.impact.core.module.buildingLocation.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("buildingService")
@RequiredArgsConstructor
public class BuildingService {
    public final BuildingRepository buildingRepository;
    public final BuildingMapper buildingMapper;

    public BuildingResponse save(BuildingRequest buildingRequest) {
        Building building = buildingMapper.toEntity(buildingRequest);
        Building savedBuilding = buildingRepository.save(building);
        return buildingMapper.toDTO(savedBuilding);
    }

    public BuildingResponse update(Integer id, BuildingRequest buildingRequest) {
        Building existingBuilding = findById(id);
        Building updatedBuilding = buildingMapper.toEntity(buildingRequest);
        updatedBuilding.setId(existingBuilding.getId());
        Building savedBuilding = buildingRepository.save(updatedBuilding);
        return buildingMapper.toDTO(savedBuilding);
    }

    public BuildingResponse delete(Integer id) {
        Building building = findById(id);
        buildingRepository.delete(building);
        return buildingMapper.toDTO(building);
    }

    public Building findById(Integer id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El edificio con el id : " + id + " no se existe."));
    }

    public List<BuildingResponse> findAll() {
        return buildingRepository.findAll()
                .stream()
                .map(buildingMapper::toDTO)
                .toList();
    }
}
