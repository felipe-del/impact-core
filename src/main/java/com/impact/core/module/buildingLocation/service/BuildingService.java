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

/**
 * Service class for handling operations related to {@link Building} entities.
 * <p>
 * Provides business logic for creating, updating, deleting, and retrieving
 * buildings using {@link BuildingRepository} and mapping them with {@link BuildingMapper}.
 */
@Service("buildingService")
@RequiredArgsConstructor
public class BuildingService {
    public final BuildingRepository buildingRepository;
    public final BuildingMapper buildingMapper;

    /**
     * Creates and saves a new {@link Building} based on the given {@link BuildingRequest}
     * Data Transfer Object (DTO), and returns the saved result as a {@link BuildingResponse}.
     *
     * @param buildingRequest the request DTO containing building data
     * @return the saved building as a response DTO
     */
    public BuildingResponse save(BuildingRequest buildingRequest) {
        Building building = buildingMapper.toEntity(buildingRequest);
        Building savedBuilding = buildingRepository.save(building);
        return buildingMapper.toDTO(savedBuilding);
    }

    /**
     * Updates an existing {@link Building} by its identifier using data from the provided
     * {@link BuildingRequest} Data Transfer Object (DTO), and returns the updated result
     * as a {@link BuildingResponse}.
     *
     * @param id the identifier of the building to update
     * @param buildingRequest the request DTO containing updated data
     * @return the updated building as a response DTO
     */
    public BuildingResponse update(Integer id, BuildingRequest buildingRequest) {
        Building existingBuilding = findById(id);
        Building updatedBuilding = buildingMapper.toEntity(buildingRequest);
        updatedBuilding.setId(existingBuilding.getId());
        Building savedBuilding = buildingRepository.save(updatedBuilding);
        return buildingMapper.toDTO(savedBuilding);
    }

    /**
     * Deletes a {@link Building} by its identifier and returns its data as a
     * {@link BuildingResponse} Data Transfer Object (DTO).
     *
     * @param id the identifier of the building to delete
     * @return the deleted building as a response DTO
     */
    public BuildingResponse delete(Integer id) {
        Building building = findById(id);
        buildingRepository.delete(building);
        return buildingMapper.toDTO(building);
    }

    /**
     * Retrieves a {@link Building} entity by its identifier.
     *
     * @param id the identifier of the building
     * @return the found building entity
     * @throws ResourceNotFoundException if no building with the given identifier exists
     */
    public Building findById(Integer id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El edificio con el id : " + id + " no se existe."));
    }

    /**
     * Retrieves all {@link Building} entities and maps them to
     * {@link BuildingResponse} Data Transfer Objects (DTOs).
     *
     * @return a list of all buildings as response DTOs
     */
    public List<BuildingResponse> findAll() {
        return buildingRepository.findAll()
                .stream()
                .map(buildingMapper::toDTO)
                .toList();
    }
}
