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

/**
 * Service class for managing space-related operations.
 * <p>
 * Provides functionality to create, update, delete, retrieve a single space by its identifier,
 * and list all existing spaces. It relies on the {@link SpaceRepository} for persistence and
 * the {@link SpaceMapper} for mapping between entity and Data Transfer Object (DTO) representations.
 */
@Service("spaceService")
@RequiredArgsConstructor
public class SpaceService {
    public final SpaceRepository spaceRepository;
    public final SpaceMapper spaceMapper;

    /**
     * Saves a new space entity to the database.
     *
     * @param spaceRequest the {@link SpaceRequest} containing input data.
     * @return the created {@link SpaceResponse} Data Transfer Object (DTO).
     */
    public SpaceResponse save(SpaceRequest spaceRequest) {
        Space space = spaceMapper.toEntity(spaceRequest);
        Space savedSpace = spaceRepository.save(space);
        return spaceMapper.toDTO(savedSpace);
    }

    /**
     * Updates an existing space by its unique identifier.
     *
     * @param id the identifier of the space to update.
     * @param spaceRequest the {@link SpaceRequest} containing updated information.
     * @return the updated {@link SpaceResponse} Data Transfer Object (DTO).
     */
    public SpaceResponse update(int id, SpaceRequest spaceRequest) {
        Space space = this.findById(id);
        Space updatedSpace = spaceMapper.toEntity(spaceRequest);
        updatedSpace.setId(space.getId());
        Space savedSpace = spaceRepository.save(updatedSpace);
        return spaceMapper.toDTO(savedSpace);
    }

    /**
     * Deletes a space by its unique identifier.
     *
     * @param id the identifier of the space to delete.
     * @return the {@link SpaceResponse} Data Transfer Object (DTO) representing the deleted entity.
     */
    public SpaceResponse delete(int id) {
        Space space = this.findById(id);
        spaceRepository.delete(space);
        return spaceMapper.toDTO(space);
    }

    /**
     * Retrieves a {@link Space} entity by its unique identifier.
     *
     * @param id the identifier of the space to find.
     * @return the corresponding {@link Space} entity.
     * @throws ResourceNotFoundException if no space is found with the given identifier.
     */
    public Space findById(int id) {
        return spaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacion con el ID: " + id + " no encontrado."));
    }

    /**
     * Retrieves all space entities from the database.
     *
     * @return a list of {@link SpaceResponse} Data Transfer Objects (DTOs).
     */
    public List<SpaceResponse> findAll() {
        return spaceRepository.findAll()
                .stream()
                .map(spaceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
