package com.impact.brain.commonSpace.service;

import com.impact.brain.commonSpace.dto.BuildingLocationDTO;
import com.impact.brain.commonSpace.dto.BuildingDTO;
import com.impact.brain.commonSpace.entity.*;
import com.impact.brain.commonSpace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

@org.springframework.stereotype.Service("spaceService")
public class SpaceService {
    @Autowired
    private SpaceTypeRepository spaceTypeRepository;

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
    public Iterable<SpaceType> spaceTypes() { return spaceTypeRepository.findAll(); }
    public Iterable<SpaceEquipment> spaceEquipments() { return spaceEquipmentRepository.findAll(); }
    public Iterable<SpaceStatus> spaceStatuses() { return spaceStatusRepository.findAll(); }
    public Iterable<Building> buildings() { return buildingRepository.findAll(); }
    public Iterable<Space> spaces() { return spaceRepository.findAll(); }

    /* Method to show all building locations, the BuildingLocationDTO
    *  is being used to avoid errors from LAZY type fetching in
    *  the attribute 'building' inside the BuildingLocation class.
    */
    private BuildingLocationDTO buildingLocationToDTO(BuildingLocation building){
        BuildingLocationDTO newLocation = new BuildingLocationDTO();

        newLocation.setId(building.getId());
        newLocation.setBuildingId(building.getBuilding().getId());
        newLocation.setFloorId(building.getFloor());

        return newLocation;
    }

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

            for(BuildingLocationDTO currentLocation : buildingLocations()) {
                if(currentBuilding.getId() == currentLocation.getBuildingId()) {
                    locationsOfCurrBuilding.getLocations().add(currentLocation);
                }
            }

            locationsOfBuildings.add(locationsOfCurrBuilding);
        }
        return locationsOfBuildings;
    }

    /* Save methods for each of the classes that have relationships with the Space class */
    public SpaceType saveSpaceType(SpaceType spaceType) {
        return spaceTypeRepository.save(spaceType);
    }

    public Space saveSpace(Space space) {
        return spaceRepository.save(space);
    }

    public Building saveBuilding(Building building){
        return buildingRepository.save(building);
    }

    public BuildingLocation saveBuildingLocation(BuildingLocation buildingLocation){
        return buildingLocationRepository.save(buildingLocation);
    }

    public void saveSpaceEquipment(SpaceEquipment spaceEquipment) {
        spaceEquipmentRepository.save(spaceEquipment);
        System.out.println("Saving spaceEquipment: " + spaceEquipment);
    }

    /* Methods to get objects */
    public Optional<SpaceType> spaceTypeById(int id) {
        return spaceTypeRepository.findById(id);
    }

    public Optional<SpaceStatus> spaceStatusById(int id) {
        return spaceStatusRepository.findById(id);
    }

    public Optional<Building> buildingById(int id) {
        return buildingRepository.findById(id);
    }

    public Optional<BuildingLocation> buildingLocationById(int id) {
        return buildingLocationRepository.findById(id);
    }
}
