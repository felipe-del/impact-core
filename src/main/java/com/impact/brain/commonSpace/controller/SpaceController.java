package com.impact.brain.commonSpace.controller;

import com.impact.brain.commonSpace.dto.BuildingLocationDTO;
import com.impact.brain.commonSpace.dto.BuildingDTO;
import com.impact.brain.commonSpace.dto.SpaceDTO;
import com.impact.brain.commonSpace.entity.*;
import com.impact.brain.commonSpace.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

    @GetMapping("/building")
    public Iterable<Building> findBuildings() { return spaceService.buildings(); }

    @GetMapping("/building-locations")
    public Iterable<BuildingLocationDTO> findBuildingLocations() { return spaceService.buildingLocations(); }

    @GetMapping("/locations-by-building")
    public Iterable<BuildingDTO> findLocationsByBuilding() { return spaceService.locationsByBuilding(); }

    // Methods are missing their implementation
    @PostMapping("/create")
    public void createSpace(@RequestBody SpaceDTO space) {
        try{
            Space newSpace = new Space();
            newSpace.setId(0);
            newSpace.setName(space.getName());
            newSpace.setSpaceCode(space.getSpaceCode());
            newSpace.setMaxPeople(space.getMaxPeople());
            newSpace.setLocation(new BuildingLocation());
            newSpace.setIsDeleted(false);

            // Adding the spaceType, spaceStatus, buildingLocation and Building using an idSearch;
            Optional<BuildingLocation> bLoc = spaceService.buildingLocationById(space.getBuildingLocation());
            bLoc.ifPresent(newSpace::setLocation);

            Optional<SpaceType> bType = spaceService.spaceTypeById(space.getSpaceType());
            bType.ifPresent(newSpace::setType);

            Optional<SpaceStatus> bStatus = spaceService.spaceStatusById(space.getSpaceStatus());
            bStatus.ifPresent(newSpace::setStatus);

            System.out.println(bLoc.get().getFloor());
            System.out.println(bType.get().getType());
            System.out.println(bStatus.get().getName());

            spaceService.saveSpace(newSpace);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    // Method needs refinement in case a bad string is sent
    @PostMapping("/create/space-type")
    public void createSpaceType(@RequestBody String type) {
        try {
            SpaceType spaceType = new SpaceType();
            spaceType.setId(0);
            spaceType.setType(type);
            spaceService.saveSpaceType(spaceType);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    // Method needs refinement in case a bad string is sent
    @PostMapping("/create/building")
    public void createBuilding(@RequestBody String buildingName) {
        try {
            Building building = new Building();
            building.setId(0);
            building.setName(buildingName);
            spaceService.saveBuilding(building);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/create/building-location")
    public void createBuildingLocation(@RequestBody BuildingLocationDTO buildingLocationDTO) {
        try {
            BuildingLocation buildingLocation = new BuildingLocation();
            buildingLocation.setId(0);
            buildingLocation.setFloor(buildingLocationDTO.getFloorId());

            Optional<Building> building = spaceService.buildingById(buildingLocationDTO.getBuildingId());
            building.ifPresent(buildingLocation::setBuilding);

            spaceService.saveBuildingLocation(buildingLocation);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    // Methods are missing their implementation
    @PostMapping("/create/equipment")
    public Space createSpaceEquipment(@RequestBody SpaceEquipment spaceEquipment) {
        return null;
    }
}
