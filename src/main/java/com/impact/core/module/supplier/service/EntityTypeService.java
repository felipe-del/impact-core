package com.impact.core.module.supplier.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.enun.EEntityType;
import com.impact.core.module.supplier.mapper.EntityTypeMapper;
import com.impact.core.module.supplier.payload.response.EntityTypeResponse;
import com.impact.core.module.supplier.repository.EntityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling operations related to {@link EntityType}.
 * <p>
 * This service provides methods for retrieving {@link EntityType} records, both by name and all available records.
 */
@Service("entityTypeService")
@RequiredArgsConstructor
public class EntityTypeService {

    public final EntityTypeRepository entityTypeRepository;
    public final EntityTypeMapper entityTypeMapper;

    /**
     * Finds an {@link EntityType} by its name.
     * <p>
     * This method retrieves an {@link EntityType} based on the provided {@link EEntityType} enum.
     * If the entity is not found, a {@link ResourceNotFoundException} is thrown.
     *
     * @param name The {@link EEntityType} representing the name of the entity type.
     * @return The {@link EntityType} object matching the given name.
     * @throws ResourceNotFoundException if the {@link EntityType} is not found.
     */
    public EntityType findByName(EEntityType name) {
        return entityTypeRepository.findByTypeName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de entidad " + name + " no encontrada."));
    }


    /**
     * Retrieves a list of all available {@link EntityTypeResponse} DTOs.
     * <p>
     * This method fetches all {@link EntityType} records from the database, maps them to DTOs using the {@link EntityTypeMapper},
     * and returns a list of {@link EntityTypeResponse}.
     *
     * @return A list of {@link EntityTypeResponse} objects.
     */
    public List<EntityTypeResponse> findAll() {
        return entityTypeRepository.findAll().stream()
                .map(entityTypeMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

}
