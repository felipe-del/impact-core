package com.impact.brain.commonSpace.controller;

import com.impact.brain.commonSpace.dto.BuildingDTO;
import com.impact.brain.commonSpace.dto.BuildingLocationDTO;
import com.impact.brain.commonSpace.dto.SpaceDTO;
import com.impact.brain.commonSpace.dto.SpaceRequestInformationDTO;
import com.impact.brain.commonSpace.dto.SpaceEquipmentDTO;
import com.impact.brain.commonSpace.entity.*;
import com.impact.brain.commonSpace.service.SpaceService;
import com.impact.brain.user.service.impl.UserService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/common-space")
public class SpaceController {
    @Autowired
    SpaceService spaceService;

    @Autowired
    UserService userService;

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

    @GetMapping("/requests")
    public Iterable<SpaceRequest> findSpaceRequests() { return spaceService.spaceRequests(); }

    @GetMapping("/reservations")
    public Iterable<SpaceReservation> findReservations() { return spaceService.spaceReservations(); }

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

    @PostMapping("/create/equipment")
    public SpaceEquipment createSpaceEquipment(@RequestBody SpaceEquipmentDTO spaceEquipment) {
        try {return spaceService.saveSpaceEquipment(spaceEquipment);}
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/find/space/{spaceId}")
    public Space findSpaceById(@PathVariable int spaceId) {
        try { return spaceService.spaceById(spaceId).get(); }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/space/{spaceId}")
    public Space updateSpace(@PathVariable int spaceId, @RequestBody SpaceDTO spaceToEdit) {
        try { return spaceService.editSpace(spaceId, spaceToEdit); }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/request/space-request&reservation")
    public Pair<SpaceRequest, SpaceReservation> createSpaceRequest(@RequestBody SpaceRequestInformationDTO spaceRequest) {
        try { return spaceService.saveSpaceRequestAndReservation(spaceRequest, userService.findById(1)); }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
