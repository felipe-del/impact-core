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

@Component
@RequiredArgsConstructor
public class SpaceEquipmentMapper {

    public final BrandService brandService;
    public final SpaceService spaceService;
    public final BrandMapper brandMapper;
    public final SpaceMapper spaceMapper;

    public SpaceEquipment toEntity(SpaceEquipmentRequest request) {
        return SpaceEquipment.builder()
                .name(request.getName())
                .brand(brandService.findById(request.getBrandId()))
                .space(spaceService.findById(request.getSpaceId()))
                .quantity(request.getQuantity())
                .build();
    }

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
