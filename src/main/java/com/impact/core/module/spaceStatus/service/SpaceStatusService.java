package com.impact.core.module.spaceStatus.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import com.impact.core.module.spaceStatus.enun.ESpaceStatus;
import com.impact.core.module.spaceStatus.mapper.SpaceStatusMapper;
import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import com.impact.core.module.spaceStatus.repository.SpaceStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("spaceStatusService")
@RequiredArgsConstructor
public class SpaceStatusService {
    public final SpaceStatusRepository spaceStatusRepository;
    public final SpaceStatusMapper spaceStatusMapper;

    public List<SpaceStatusResponse> findAll() {
        return spaceStatusRepository.findAll().stream()
                .map(spaceStatusMapper::toDTO)
                .toList();
    }

    public SpaceStatus findById(int id) {
        return spaceStatusRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El estado de espacio : id-" + id + " no se encuentra en la base de datos"));
    }

    public SpaceStatus findByName(ESpaceStatus name) {
        return spaceStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("El estado de espacio : " + name + " no se encuentra en la base de datos"));
    }

}
