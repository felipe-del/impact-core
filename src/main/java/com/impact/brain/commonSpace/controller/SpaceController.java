package com.impact.brain.commonSpace.controller;

import com.impact.brain.commonSpace.entity.Space;
import com.impact.brain.commonSpace.entity.SpaceEquipment;
import com.impact.brain.commonSpace.entity.SpaceStatus;
import com.impact.brain.commonSpace.entity.SpaceType;
import com.impact.brain.commonSpace.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common-space")
public class SpaceController {
    @Autowired
    SpaceService spaceService;

    @GetMapping("")
    public Iterable<Space> getSpaces() { return spaceService.spaces(); }

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
