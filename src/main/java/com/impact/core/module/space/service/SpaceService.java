package com.impact.core.module.space.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.space.mapper.SpaceMapper;
import com.impact.core.module.space.payload.request.SpaceRequest;
import com.impact.core.module.space.payload.response.SpaceResponse;
import com.impact.core.module.space.respository.SpaceRepository;
import com.impact.core.module.space.entity.Space;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("spaceService")
@RequiredArgsConstructor
public class SpaceService {
    public final SpaceRepository spaceRepository;
    public final SpaceMapper spaceMapper;

    public SpaceResponse save(SpaceRequest spaceRequest) {
        Space space = spaceMapper.toEntity(spaceRequest);
        Space savedSpace = spaceRepository.save(space);
        return spaceMapper.toDTO(savedSpace);
    }

    public SpaceResponse update(int id, SpaceRequest spaceRequest) {
        Space space = this.findById(id);
        Space updatedSpace = spaceMapper.toEntity(spaceRequest);
        updatedSpace.setId(space.getId());
        Space savedSpace = spaceRepository.save(updatedSpace);
        return spaceMapper.toDTO(savedSpace);
    }

    public SpaceResponse delete(int id) {
        Space space = this.findById(id);
        spaceRepository.delete(space);
        return spaceMapper.toDTO(space);
    }

    public Space findById(int id) {
        return spaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacion con el ID: " + id + " no encontrado."));
    }

    public List<SpaceResponse> findAll() {
        return spaceRepository.findAll()
                .stream()
                .map(spaceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
