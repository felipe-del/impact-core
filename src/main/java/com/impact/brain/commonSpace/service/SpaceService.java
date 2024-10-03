package com.impact.brain.commonSpace.service;

import com.impact.brain.commonSpace.dto.BuildingLocationDTO;
import com.impact.brain.commonSpace.dto.BuildingDTO;
import com.impact.brain.commonSpace.dto.SpaceDTO;
import com.impact.brain.commonSpace.entity.*;
import com.impact.brain.commonSpace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@org.springframework.stereotype.Service("spaceService")
public class SpaceService {
    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SpaceEquipmentRepository spaceEquipmentRepository;

    @Autowired
    private SpaceStatusRepository spaceStatusRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingLocationRepository buildingLocationRepository;

    /* FindAll() methods to retrieve the entire list of each object type respectively  */
    public Iterable<SpaceEquipment> spaceEquipments() { return spaceEquipmentRepository.findAll(); }
    public Iterable<SpaceStatus> spaceStatuses() { return spaceStatusRepository.findAll(); }
    public Iterable<Building> buildings() { return buildingRepository.findAll(); }
    public Iterable<Space> spaces() { return spaceRepository.findAll(); }

    /* Method to show all building locations, the BuildingLocationDTO
    *  is being used to avoid errors from LAZY type fetching in
    *  the attribute 'building' inside the BuildingLocation class.
    *
    *  This same logic will be applied to all 'conversion methods' that
    *  will follow
    */
    private BuildingLocationDTO buildingLocationToDTO(BuildingLocation building){
        BuildingLocationDTO newLocation = new BuildingLocationDTO();

        newLocation.setId(building.getId());
        newLocation.setBuildingId(building.getBuilding().getId());
        newLocation.setFloorId(building.getFloor());

        return newLocation;
    }

    private BuildingLocation buildingLocationDTOToBuildingLocation(BuildingLocationDTO buildingLocation){
        BuildingLocation newLocation = new BuildingLocation();

        newLocation.setId(0);
        newLocation.setFloor(buildingLocation.getFloorId());

        Optional<Building> building = buildingById(buildingLocation.getBuildingId());
        building.ifPresent(newLocation::setBuilding);

        return newLocation;
    }

    private Space spaceDTOToSpace(SpaceDTO space){
        Space newSpace = new Space();

        newSpace.setId(0);
        newSpace.setName(space.getName());
        newSpace.setSpaceCode(space.getSpaceCode());
        newSpace.setMaxPeople(space.getMaxPeople());
        newSpace.setOpenTime(space.getOpenTime());
        newSpace.setCloseTime(space.getCloseTime());
        newSpace.setIsDeleted(false);

        Optional<BuildingLocation> buildingLocation = buildingLocationById(space.getBuildingLocation());
        buildingLocation.ifPresent(newSpace::setLocation);

        Optional<SpaceStatus> spaceStatus = spaceStatusById(space.getSpaceStatus());
        spaceStatus.ifPresent(newSpace::setStatus);

        return newSpace;
    }

    // --------------------------------------------------------------------------------------------------------------------

    public Iterable<BuildingLocationDTO> buildingLocations() {
        ArrayList<BuildingLocationDTO> buildingLocations = new ArrayList<>();
        for(BuildingLocation buildingLocation : buildingLocationRepository.findAll()) {
            buildingLocations.add(buildingLocationToDTO(buildingLocation));
        }
        return buildingLocations;
    }

    // Method to sort all locations by their corresponding buildings
    public Iterable<BuildingDTO> locationsByBuilding() {
        ArrayList<BuildingDTO> locationsOfBuildings = new ArrayList<>();

        for(Building currentBuilding : buildingRepository.findAll()) {

            BuildingDTO locationsOfCurrBuilding = new BuildingDTO();
            locationsOfCurrBuilding.setBuilding(currentBuilding);

            for(BuildingLocationDTO bLoc : buildingLocations()){
                if(currentBuilding.getId() == bLoc.getBuildingId()){
                    locationsOfCurrBuilding.getLocations().add(bLoc);
                }
            }

            locationsOfBuildings.add(locationsOfCurrBuilding);
        }

        return locationsOfBuildings;
    }

    /* Save methods ---------------------------------------------------------------------------------- */
    public Space saveSpace(SpaceDTO space) {
        return spaceRepository.save(spaceDTOToSpace(space));
    }

    public Building saveBuilding(Building building){
        return buildingRepository.save(building);
    }

    public BuildingLocation saveBuildingLocation(BuildingLocationDTO buildingLocation){
        return buildingLocationRepository.save(buildingLocationDTOToBuildingLocation(buildingLocation));
    }

    public void saveSpaceEquipment(SpaceEquipment spaceEquipment) {
        spaceEquipmentRepository.save(spaceEquipment);
        System.out.println("Saving spaceEquipment: " + spaceEquipment);
    }

    /* Methods to get objects */
    public Optional<Space> spaceById(int id) { return spaceRepository.findById(id); }

    public Optional<SpaceStatus> spaceStatusById(int id) {
        return spaceStatusRepository.findById(id);
    }

    public Optional<Building> buildingById(int id) {
        return buildingRepository.findById(id);
    }

    public Optional<BuildingLocation> buildingLocationById(int id) {
        return buildingLocationRepository.findById(id);
    }

    /* Edit methods */
    public Space editSpace(int spaceId, SpaceDTO newSpaceData){
        Optional<Space> foundSpace = spaceById(spaceId);
        if(foundSpace.isPresent()){
            Space spaceToUpdate = foundSpace.get();
            spaceToUpdate.setName(newSpaceData.getName());
            spaceToUpdate.setSpaceCode(newSpaceData.getSpaceCode());
            spaceToUpdate.setMaxPeople(newSpaceData.getMaxPeople());
            spaceToUpdate.setOpenTime(newSpaceData.getOpenTime());
            spaceToUpdate.setCloseTime(newSpaceData.getCloseTime());

            Optional<BuildingLocation> buildingLocation = buildingLocationById(newSpaceData.getBuildingLocation());
            buildingLocation.ifPresent(spaceToUpdate::setLocation);

            Optional<SpaceStatus> spaceStatus = spaceStatusById(newSpaceData.getSpaceStatus());
            spaceStatus.ifPresent(spaceToUpdate::setStatus);

            return spaceRepository.save(spaceToUpdate);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
