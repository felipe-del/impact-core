package com.impact.core.module.productCategory.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.productCategory.entity.UnitOfMeasurement;
import com.impact.core.module.productCategory.mapper.UnitOfMeasurementMapper;
import com.impact.core.module.productCategory.payload.response.UnitOfMeasurementResponse;
import com.impact.core.module.productCategory.repository.UnitOfMeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing {@link UnitOfMeasurement} entities.
 * Provides methods to retrieve {@link UnitOfMeasurement} data from the repository
 * and map it to the corresponding {@link UnitOfMeasurementResponse}.
 */
@Service("unitOfMeasurementService")
@RequiredArgsConstructor
public class UnitOfMeasurementService {
    public final UnitOfMeasurementRepository unitOfMeasurementRepository;
    public final UnitOfMeasurementMapper unitOfMeasurementMapper;

    /**
     * Retrieves all {@link UnitOfMeasurement} entities from the repository and converts them into DTOs.
     *
     * @return a list of {@link UnitOfMeasurementResponse} DTOs representing all units of measurement.
     */
    public List<UnitOfMeasurementResponse> findAll() {
        return unitOfMeasurementRepository.findAll().stream()
                .map(unitOfMeasurementMapper::toDTO)
                .toList();
    }

    /**
     * Retrieves a {@link UnitOfMeasurement} by its ID from the repository.
     *
     * @param id the ID of the unit of measurement to retrieve.
     * @return the {@link UnitOfMeasurement} entity.
     * @throws ResourceNotFoundException if no {@link UnitOfMeasurement} is found with the specified ID.
     */
    public UnitOfMeasurement findById(Integer id) {
        return unitOfMeasurementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La unidad de medida con el id " + id + " no existe."));
    }
}
