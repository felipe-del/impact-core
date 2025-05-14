package com.impact.core.module.space_equipment.mapper;

import com.impact.core.module.brand.mapper.BrandMapper;
import com.impact.core.module.brand.service.BrandService;
import com.impact.core.module.space.mapper.SpaceMapper;
import com.impact.core.module.space.service.SpaceService;
import com.impact.core.module.space_equipment.entity.SpaceEquipment;
import com.impact.core.module.space_equipment.payload.request.SpaceEquipmentRequest;
import com.impact.core.module.space_equipment.payload.response.SpaceEquipmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class that converts between SpaceEquipmentRequest, SpaceEquipment, and SpaceEquipmentResponse objects.
 * <p>
 * This class provides the logic for converting the data between the request and response DTOs (Data Transfer Objects)
 * and the corresponding entity. It also handles the conversion of related entities like Brand and Space via their
 * respective mappers and services.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class SpaceEquipmentMapper {

    public final BrandService brandService;
    public final SpaceService spaceService;
    public final BrandMapper brandMapper;
    public final SpaceMapper spaceMapper;

    /**
     * Converts a SpaceEquipmentRequest DTO to a SpaceEquipment entity.
     * <p>
     * This method maps the fields from the SpaceEquipmentRequest DTO to a new SpaceEquipment entity, including the
     * related brand and space entities, which are fetched via their respective services using the IDs provided in the
     * request.
     * </p>
     *
     * @param request The SpaceEquipmentRequest DTO containing the data to map.
     * @return The corresponding SpaceEquipment entity with the mapped data.
     */
    public SpaceEquipment toEntity(SpaceEquipmentRequest request) {
        return SpaceEquipment.builder()
                .name(request.getName())
                .brand(brandService.findById(request.getBrandId()))
                .space(spaceService.findById(request.getSpaceId()))
                .quantity(request.getQuantity())
                .build();
    }

    /**
     * Converts a SpaceEquipment entity to a SpaceEquipmentResponse DTO.
     * <p>
     * This method maps the fields from the SpaceEquipment entity to a SpaceEquipmentResponse DTO, including the
     * related brand and space entities, which are mapped via the respective mappers.
     * </p>
     *
     * @param spaceEquipment The SpaceEquipment entity to map.
     * @return The corresponding SpaceEquipmentResponse DTO with the mapped data.
     */
    public SpaceEquipmentResponse toDTO(SpaceEquipment spaceEquipment) {
        return SpaceEquipmentResponse.builder()
                .id(spaceEquipment.getId())
                .name(spaceEquipment.getName())
                .brandResponse(brandMapper.toDTO(spaceEquipment.getBrand()))
                .spaceResponse(spaceMapper.toDTO(spaceEquipment.getSpace()))
                .quantity(spaceEquipment.getQuantity())
                .build();
    }

}
