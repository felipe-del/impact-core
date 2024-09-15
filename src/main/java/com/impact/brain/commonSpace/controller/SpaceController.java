package com.impact.brain.commonSpace.controller;

import com.impact.brain.commonSpace.dto.SpaceDTO;
import com.impact.brain.commonSpace.entity.Space;
import com.impact.brain.commonSpace.entity.SpaceEquipment;
import com.impact.brain.commonSpace.entity.SpaceStatus;
import com.impact.brain.commonSpace.entity.SpaceType;
import com.impact.brain.commonSpace.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/common-space")
public class SpaceController {
    @Autowired
    SpaceService spaceService;

    @GetMapping("/all")
    public Iterable<SpaceDTO> getAllSpaces() {
        try {
            // Obtenemos todos los espacios
            Iterable<Space> spaces = spaceService.spaces();

            // Convertimos cada entidad Space en SpaceDTO manualmente y devolvemos un iterable
            Iterable<SpaceDTO> spaceDTOs = StreamSupport.stream(spaces.spliterator(), false)
                    .map(space -> {
                        SpaceDTO dto = new SpaceDTO();
                        dto.setId(space.getId());
                        dto.setName(space.getName());
                        dto.setSpaceCode(space.getSpaceCode());
                        dto.setLocationName(space.getLocation().getBuilding().getName());
                        dto.setMaxPeople(space.getMaxPeople());
                        dto.setTypeName(space.getType().getType());
                        dto.setStatusName(space.getStatus() != null ? space.getStatus().getName() : null);
                        return dto;
                    })
                    .collect(Collectors.toList());
            return spaceDTOs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al recuperar los espacios.");
        }
    }

    @GetMapping("/types")
    public Iterable<SpaceType> findSpaces() { return spaceService.spaceTypes(); }

    @GetMapping("/status")
    public Iterable<SpaceStatus> findStatus() { return spaceService.spaceStatuses(); }

    @GetMapping("/equipment")
    public Iterable<SpaceEquipment> findEquipment() { return spaceService.spaceEquipments(); }

    // Methods are missing their implementation
    @PostMapping("/create")
    public Space createSpace(@RequestBody Space space) {
        return null;
    }

    // Methods are missing their implementation
    @PostMapping("/create-equipment")
    public Space createSpaceEquipment(@RequestBody SpaceEquipment spaceEquipment) {
        return null;
    }
}
