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

/**
 * Service class for handling operations related to {@link BuildingLocation} entities.
 * <p>
 * Provides business logic for creating, updating, deleting, and retrieving
 * building locations using {@link BuildingLocationRepository} and mapping them with {@link BuildingLocationMapper}.
 */
@Service("buildingLocationService")
@RequiredArgsConstructor
public class BuildingLocationService {
    public final BuildingLocationRepository buildingLocationRepository;
    public final BuildingLocationMapper buildingLocationMapper;

    /**
     * Creates and saves a new {@link BuildingLocation} based on the given {@link BuildingLocationRequest}
     * Data Transfer Object (DTO), and returns the saved result as a {@link BuildingLocationResponse}.
     *
     * @param buildingLocationRequest the request DTO containing building location data
     * @return the saved building location as a response DTO
     */
    public BuildingLocationResponse save(BuildingLocationRequest buildingLocationRequest) {
        BuildingLocation buildingLocation = buildingLocationMapper.toEntity(buildingLocationRequest);
        BuildingLocation savedBuildingLocation = buildingLocationRepository.save(buildingLocation);
        return buildingLocationMapper.toDTO(savedBuildingLocation);
    }

    /**
     * Updates an existing {@link BuildingLocation} by its identifier using data from the provided
     * {@link BuildingLocationRequest} Data Transfer Object (DTO), and returns the updated result
     * as a {@link BuildingLocationResponse}.
     *
     * @param id the identifier of the building location to update
     * @param buildingLocationRequest the request DTO containing updated data
     * @return the updated building location as a response DTO
     */
    public BuildingLocationResponse update(Integer id, BuildingLocationRequest buildingLocationRequest) {
        BuildingLocation existingBuildingLocation = this.findById(id);
        BuildingLocation updatedBuildingLocation = buildingLocationMapper.toEntity(buildingLocationRequest);
        updatedBuildingLocation.setId(existingBuildingLocation.getId());
        BuildingLocation savedBuildingLocation = buildingLocationRepository.save(updatedBuildingLocation);
        return buildingLocationMapper.toDTO(savedBuildingLocation);
    }

    /**
     * Deletes a {@link BuildingLocation} by its identifier and returns its data as a
     * {@link BuildingLocationResponse} Data Transfer Object (DTO).
     *
     * @param id the identifier of the building location to delete
     * @return the deleted building location as a response DTO
     */
    public BuildingLocationResponse delete(Integer id) {
        BuildingLocation buildingLocation = this.findById(id);
        buildingLocationRepository.delete(buildingLocation);
        return buildingLocationMapper.toDTO(buildingLocation);
    }

    /**
     * Retrieves a {@link BuildingLocation} entity by its identifier.
     *
     * @param id the identifier of the building location
     * @return the found building location entity
     * @throws ResourceNotFoundException if no building location with the given identifier exists
     */
    public BuildingLocation findById(int id) {
        return buildingLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La ubicación del edificio con el id : " + id + " no se existe."));
    }

    /**
     * Retrieves all {@link BuildingLocation} entities and maps them to
     * {@link BuildingLocationResponse} Data Transfer Objects (DTOs).
     *
     * @return a list of all building locations as response DTOs
     */
    public List<BuildingLocationResponse> findAll() {
        return buildingLocationRepository.findAll()
                .stream()
                .map(buildingLocationMapper::toDTO)
                .toList();
    }

}
