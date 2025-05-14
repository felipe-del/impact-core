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

/**
 * Service class for handling business logic related to location numbers.
 * <p>
 * This service provides methods to perform CRUD operations on {@link LocationNumber} entities, including
 * finding all location numbers, saving, updating, deleting, and retrieving a location number by its ID.
 * </p>
 */
@Service("locationNumberService")
@RequiredArgsConstructor
public class LocationNumberService {
    public final LocationNumberRepository locationNumberRepository;
    public final LocationNumberMapper locationNumberMapper;

    /**
     * Retrieves all location numbers and maps them to response DTOs.
     *
     * @return a list of {@link LocationNumberResponse} objects representing all location numbers.
     */
    public List<LocationNumberResponse> findAll() {
        return locationNumberRepository.findAll().stream()
                .map(locationNumberMapper::toDTO)
                .toList();
    }

    /**
     * Saves a new location number and returns the corresponding response DTO.
     *
     * @param locationNumberRequest the data to be saved as a new location number.
     * @return a {@link LocationNumberResponse} object representing the saved location number.
     */
    public LocationNumberResponse save(LocationNumberRequest locationNumberRequest) {
        LocationNumber locationNumber = locationNumberMapper.toEntity(locationNumberRequest);
        LocationNumber savedLocationNumber = locationNumberRepository.save(locationNumber);
        return locationNumberMapper.toDTO(savedLocationNumber);
    }


    /**
     * Updates an existing location number and returns the corresponding response DTO.
     *
     * @param id the ID of the location number to be updated.
     * @param locationNumberRequest the data to update the location number.
     * @return a {@link LocationNumberResponse} object representing the updated location number.
     * @throws ResourceNotFoundException if the location number with the given ID does not exist.
     */
    public LocationNumberResponse update(int id, LocationNumberRequest locationNumberRequest) {
        LocationNumber locationNumber = this.findById(id);
        LocationNumber updatedLocationNumber = locationNumberMapper.toEntity(locationNumberRequest);
        updatedLocationNumber.setId(locationNumber.getId());
        LocationNumber savedLocationNumber = locationNumberRepository.save(updatedLocationNumber);
        return locationNumberMapper.toDTO(savedLocationNumber);
    }

    /**
     * Deletes the location number with the given ID and returns the corresponding response DTO.
     *
     * @param id the ID of the location number to be deleted.
     * @return a {@link LocationNumberResponse} object representing the deleted location number.
     * @throws ResourceNotFoundException if the location number with the given ID does not exist.
     */
    public LocationNumberResponse delete(int id) {
        LocationNumber locationNumber = this.findById(id);
        locationNumberRepository.delete(locationNumber);
        return locationNumberMapper.toDTO(locationNumber);
    }

    /**
     * Retrieves a location number by its ID.
     *
     * @param id the ID of the location number to retrieve.
     * @return the {@link LocationNumber} entity corresponding to the given ID.
     * @throws ResourceNotFoundException if the location number with the given ID does not exist.
     */
    public LocationNumber findById(int id) {
        return locationNumberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El número: " + id + " de ubicación no existe."));
    }

}
