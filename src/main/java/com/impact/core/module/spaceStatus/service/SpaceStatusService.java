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

/**
 * Service class responsible for handling business logic related to {@link SpaceStatus}.
 * <p>
 * This service provides methods to retrieve {@link SpaceStatus} entities by their ID or name, as well as a method to retrieve
 * all {@link SpaceStatusResponse} DTOs. It interacts with the {@link SpaceStatusRepository} to perform database operations.
 */
@Service("spaceStatusService")
@RequiredArgsConstructor
public class SpaceStatusService {
    public final SpaceStatusRepository spaceStatusRepository;
    public final SpaceStatusMapper spaceStatusMapper;

    /**
     * Retrieves all {@link SpaceStatusResponse} DTOs.
     * <p>
     * This method returns a list of all {@link SpaceStatusResponse} DTOs. The {@link SpaceStatusMapper} is used to convert the
     * {@link SpaceStatus} entities into DTOs.
     *
     * @return A list of {@link SpaceStatusResponse} DTOs.
     */
    public List<SpaceStatusResponse> findAll() {
        return spaceStatusRepository.findAll().stream()
                .map(spaceStatusMapper::toDTO)
                .toList();
    }

    /**
     * Retrieves a {@link SpaceStatus} entity by its ID.
     * <p>
     * This method retrieves the {@link SpaceStatus} with the specified {@code id}. If no such entity is found, a
     * {@link ResourceNotFoundException} is thrown.
     *
     * @param id The ID of the {@link SpaceStatus} entity.
     * @return The {@link SpaceStatus} entity with the specified ID.
     * @throws ResourceNotFoundException If no {@link SpaceStatus} entity is found with the specified ID.
     */
    public SpaceStatus findById(int id) {
        return spaceStatusRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El estado de espacio : id-" + id + " no se encuentra en la base de datos"));
    }

    /**
     * Retrieves a {@link SpaceStatus} entity by its name.
     * <p>
     * This method retrieves the {@link SpaceStatus} with the specified {@code name}. If no such entity is found, a
     * {@link ResourceNotFoundException} is thrown.
     *
     * @param name The name of the {@link SpaceStatus} entity (of type {@link ESpaceStatus}).
     * @return The {@link SpaceStatus} entity with the specified name.
     * @throws ResourceNotFoundException If no {@link SpaceStatus} entity is found with the specified name.
     */
    public SpaceStatus findByName(ESpaceStatus name) {
        return spaceStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("El estado de espacio : " + name + " no se encuentra en la base de datos"));
    }

}
