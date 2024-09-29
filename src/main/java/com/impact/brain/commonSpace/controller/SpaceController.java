package com.impact.brain.commonSpace.controller;

import com.impact.brain.commonSpace.dto.BuildingDTO;
import com.impact.brain.commonSpace.dto.BuildingLocationDTO;
import com.impact.brain.commonSpace.dto.SpaceDTO;
import com.impact.brain.commonSpace.entity.*;
import com.impact.brain.commonSpace.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/common-space")
public class SpaceController {
    @Autowired
    SpaceService spaceService;
  
    @GetMapping("/all")
    public Iterable<Space> getSpaces() { return spaceService.spaces(); }

    @GetMapping("/status")
    public Iterable<SpaceStatus> findStatus() { return spaceService.spaceStatuses(); }

    @GetMapping("/equipment")
    public Iterable<SpaceEquipment> findEquipment() { return spaceService.spaceEquipments(); }

    @GetMapping("/building")
    public Iterable<Building> findBuildings() { return spaceService.buildings(); }

    @GetMapping("/building-locations")
    public Iterable<BuildingLocationDTO> findBuildingLocations() { return spaceService.buildingLocations(); }

    @GetMapping("/locations-by-building")
    public Iterable<BuildingDTO> findLocationsByBuilding() { return spaceService.locationsByBuilding(); }

    @PostMapping("/create")
    public Space createSpace(@RequestBody SpaceDTO space) {
        try { return spaceService.saveSpace(space); }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/create/building")
    public Building createBuilding(@RequestBody Building building) {
        try { return spaceService.saveBuilding(building); }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/create/building-location")
    public BuildingLocation createBuildingLocation(@RequestBody BuildingLocationDTO buildingLocationDTO) {
        try { return spaceService.saveBuildingLocation(buildingLocationDTO); }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    // Method missing implementation
    @PostMapping("/create/equipment")
    public Space createSpaceEquipment(@RequestBody SpaceEquipment spaceEquipment) {
        return null;
    }

    @GetMapping("/find/space/{spaceId}")
    public Space findSpaceById(@PathVariable int spaceId) {
        try { return spaceService.spaceById(spaceId).get(); }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
