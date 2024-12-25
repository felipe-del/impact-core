package com.impact.core.module.supplier.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.enun.EEntityType;
import com.impact.core.module.supplier.repository.EntityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("entityTypeService")
@RequiredArgsConstructor
public class EntityTypeService {

    public final EntityTypeRepository entityTypeRepository;

    public EntityType findByName(EEntityType name) {
        return entityTypeRepository.findByTypeName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de entidad " + name + " no encontrada."));
    }
}
