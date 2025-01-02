package com.impact.core.module.productCategory.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.productCategory.entity.UnitOfMeasurement;
import com.impact.core.module.productCategory.mapper.UnitOfMeasurementMapper;
import com.impact.core.module.productCategory.payload.response.UnitOfMeasurementResponse;
import com.impact.core.module.productCategory.repository.UnitOfMeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("unitOfMeasurementService")
@RequiredArgsConstructor
public class UnitOfMeasurementService {
    public final UnitOfMeasurementRepository unitOfMeasurementRepository;
    public final UnitOfMeasurementMapper unitOfMeasurementMapper;

    public List<UnitOfMeasurementResponse> findAll() {
        return unitOfMeasurementRepository.findAll().stream()
                .map(unitOfMeasurementMapper::toDTO)
                .toList();
    }

    public UnitOfMeasurement findById(Integer id) {
        return unitOfMeasurementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La unidad de medida con el id " + id + " no existe."));
    }
}
