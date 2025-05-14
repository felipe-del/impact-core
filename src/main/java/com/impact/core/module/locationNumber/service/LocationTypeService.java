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

/**
 * Service class for handling business logic related to location types.
 * <p>
 * This service provides methods to perform CRUD operations on {@link LocationType} entities, including
 * saving, updating, deleting, and retrieving location types by their ID. It also handles mapping between
 * the entity and the corresponding Data Transfer Object (DTO).
 * </p>
 */
@Service("locationTypeService")
@RequiredArgsConstructor
public class LocationTypeService {
    public final LocationTypeRepository locationTypeRepository;
    public final LocationTypeMapper locationTypeMapper;

    /**
     * Saves a new location type and returns the corresponding response DTO.
     *
     * @param locationTypeRequest the data to be saved as a new location type.
     * @return a {@link LocationTypeResponse} object representing the saved location type.
     */
    public LocationTypeResponse save(LocationTypeRequest locationTypeRequest) {
        LocationType locationType = locationTypeMapper.toEntity(locationTypeRequest);
        LocationType savedLocationType = locationTypeRepository.save(locationType);

        return locationTypeMapper.toDTO(savedLocationType);
    }

    /**
     * Updates an existing location type and returns the corresponding response DTO.
     *
     * @param id the ID of the location type to be updated.
     * @param locationTypeRequest the data to update the location type.
     * @return a {@link LocationTypeResponse} object representing the updated location type.
     * @throws ResourceNotFoundException if the location type with the given ID does not exist.
     */
    public LocationTypeResponse update(Integer id, LocationTypeRequest locationTypeRequest) {
        LocationType locationType = findById(id);
        locationType.setTypeName(locationTypeRequest.getTypeName());
        LocationType updatedLocationType = locationTypeRepository.save(locationType);

        return locationTypeMapper.toDTO(updatedLocationType);
    }

    /**
     * Deletes the location type with the given ID and returns the corresponding response DTO.
     *
     * @param id the ID of the location type to be deleted.
     * @return a {@link LocationTypeResponse} object representing the deleted location type.
     * @throws ResourceNotFoundException if the location type with the given ID does not exist.
     */
    public LocationTypeResponse delete(Integer id) {
        LocationType locationType = findById(id);
        locationTypeRepository.delete(locationType);

        return locationTypeMapper.toDTO(locationType);
    }

    /**
     * Retrieves a location type by its ID.
     *
     * @param id the ID of the location type to retrieve.
     * @return the {@link LocationType} entity corresponding to the given ID.
     * @throws ResourceNotFoundException if the location type with the given ID does not exist.
     */
    public LocationType findById(Integer id) {
        return locationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La locationType con id " + id + " no existe"));
    }

    /**
     * Retrieves all location types and maps them to response DTOs.
     *
     * @return a list of {@link LocationTypeResponse} objects representing all location types.
     */
    public List<LocationTypeResponse> findAll() {
        return locationTypeRepository.findAll().stream()
                .map(locationTypeMapper::toDTO)
                .toList();
    }
}
