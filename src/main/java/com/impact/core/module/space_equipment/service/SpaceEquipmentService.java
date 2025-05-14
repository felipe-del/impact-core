package com.impact.core.module.space_equipment.service;


import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.brand.service.BrandService;
import com.impact.core.module.space.service.SpaceService;
import com.impact.core.module.space_equipment.entity.SpaceEquipment;
import com.impact.core.module.space_equipment.mapper.SpaceEquipmentMapper;
import com.impact.core.module.space_equipment.payload.request.SpaceEquipmentRequest;
import com.impact.core.module.space_equipment.payload.response.SpaceEquipmentResponse;
import com.impact.core.module.space_equipment.repository.SpaceEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing {@link SpaceEquipment} entities.
 * <p>
 * This class provides methods for CRUD operations related to space equipment, such as retrieving, saving, updating, and
 * deleting space equipment records. It interacts with the repository layer and uses mappers to convert between entities
 * and {@link SpaceEquipmentResponse} Data Transfer Objects (DTOs).
 * </p>
 */
@Service("spaceEquipmentService")
@RequiredArgsConstructor
public class SpaceEquipmentService {

    public final SpaceEquipmentRepository spaceEquipmentRepository;
    public final SpaceEquipmentMapper spaceEquipmentMapper;
    public final BrandService brandService;
    public final SpaceService spaceService;

    /**
     * Retrieves a list of all space equipment and converts them to DTOs.
     * <p>
     * This method retrieves all {@link SpaceEquipment} entities from the repository, maps them to their respective
     * {@link SpaceEquipmentResponse} DTOs, and returns the list of DTOs.
     * </p>
     *
     * @return A list of {@link SpaceEquipmentResponse} DTOs representing all space equipment records.
     */
    public List<SpaceEquipmentResponse> findAll() {
        return spaceEquipmentRepository.findAll().stream()
                .map(spaceEquipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new {@link SpaceEquipment} entity and returns its corresponding DTO.
     * <p>
     * This method maps the provided {@link SpaceEquipmentRequest} to a {@link SpaceEquipment} entity, saves it to the
     * repository, and returns the saved space equipment as a {@link SpaceEquipmentResponse} DTO.
     * </p>
     *
     * @param spaceEquipmentRequest The request object containing the details of the space equipment to save.
     * @return The saved {@link SpaceEquipmentResponse} DTO.
     */
    public SpaceEquipmentResponse save(SpaceEquipmentRequest spaceEquipmentRequest) {
        SpaceEquipment spaceEquipment = spaceEquipmentRepository.save(spaceEquipmentMapper.toEntity(spaceEquipmentRequest));

        return spaceEquipmentMapper.toDTO(spaceEquipment);
    }

    /**
     * Updates an existing {@link SpaceEquipment} entity and returns the updated DTO.
     * <p>
     * This method retrieves the space equipment by its ID, updates its properties based on the provided
     * {@link SpaceEquipmentRequest}, and saves the updated entity. It then returns the updated space equipment as a
     * {@link SpaceEquipmentResponse} DTO.
     * </p>
     *
     * @param id                    The ID of the space equipment to update.
     * @param spaceEquipmentRequest The request object containing the updated details of the space equipment.
     * @return The updated {@link SpaceEquipmentResponse} DTO.
     * @throws ResourceNotFoundException if no space equipment is found with the given ID.
     */
    public SpaceEquipmentResponse update(int id, SpaceEquipmentRequest spaceEquipmentRequest) {
        SpaceEquipment spaceEquipment = spaceEquipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo de espacio no encontrado."));

        spaceEquipment.setName(spaceEquipmentRequest.getName());
        spaceEquipment.setBrand(brandService.findById(spaceEquipmentRequest.getBrandId()));
        spaceEquipment.setSpace(spaceService.findById(spaceEquipmentRequest.getSpaceId()));
        spaceEquipment.setQuantity(spaceEquipmentRequest.getQuantity());

        return spaceEquipmentMapper.toDTO(spaceEquipmentRepository.save(spaceEquipment));
    }

    /**
     * Deletes a {@link SpaceEquipment} entity by its ID and returns the deleted entity as a DTO.
     * <p>
     * This method retrieves the space equipment by its ID, deletes it from the repository, and returns the deleted
     * space equipment as a {@link SpaceEquipmentResponse} DTO.
     * </p>
     *
     * @param id The ID of the space equipment to delete.
     * @return The deleted {@link SpaceEquipmentResponse} DTO.
     * @throws ResourceNotFoundException if no space equipment is found with the given ID.
     */
    public SpaceEquipmentResponse delete(int id) {
        SpaceEquipment spaceEquipment = spaceEquipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo de espacio no encontrado."));

        spaceEquipmentRepository.delete(spaceEquipment);

        return spaceEquipmentMapper.toDTO(spaceEquipment);
    }
}
