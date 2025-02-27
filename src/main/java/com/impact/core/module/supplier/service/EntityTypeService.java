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

@Service("entityTypeService")
@RequiredArgsConstructor
public class EntityTypeService {

    public final EntityTypeRepository entityTypeRepository;
    public final EntityTypeMapper entityTypeMapper;

    public EntityType findByName(EEntityType name) {
        return entityTypeRepository.findByTypeName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de entidad " + name + " no encontrada."));
    }

    public List<EntityTypeResponse> findAll() {
        return entityTypeRepository.findAll().stream()
                .map(entityTypeMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

}
