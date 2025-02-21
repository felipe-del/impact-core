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

@Service("spaceEquipmentService")
@RequiredArgsConstructor
public class SpaceEquipmentService {

    public final SpaceEquipmentRepository spaceEquipmentRepository;
    public final SpaceEquipmentMapper spaceEquipmentMapper;
    public final BrandService brandService;
    public final SpaceService spaceService;

    public List<SpaceEquipmentResponse> findAll() {
        return spaceEquipmentRepository.findAll().stream()
                .map(spaceEquipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SpaceEquipmentResponse save(SpaceEquipmentRequest spaceEquipmentRequest) {
        SpaceEquipment spaceEquipment = spaceEquipmentRepository.save(spaceEquipmentMapper.toEntity(spaceEquipmentRequest));

        return spaceEquipmentMapper.toDTO(spaceEquipment);
    }

    public SpaceEquipmentResponse update(int id, SpaceEquipmentRequest spaceEquipmentRequest) {
        SpaceEquipment spaceEquipment = spaceEquipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo de espacio no encontrado."));

        spaceEquipment.setName(spaceEquipmentRequest.getName());
        spaceEquipment.setBrand(brandService.findById(spaceEquipmentRequest.getBrandId()));
        spaceEquipment.setSpace(spaceService.findById(spaceEquipmentRequest.getSpaceId()));
        spaceEquipment.setQuantity(spaceEquipmentRequest.getQuantity());

        return spaceEquipmentMapper.toDTO(spaceEquipmentRepository.save(spaceEquipment));
    }

    public SpaceEquipmentResponse delete(int id) {
        SpaceEquipment spaceEquipment = spaceEquipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo de espacio no encontrado."));

        spaceEquipmentRepository.delete(spaceEquipment);

        return spaceEquipmentMapper.toDTO(spaceEquipment);
    }
}
