package com.impact.brain.commonSpace.service;

import com.impact.brain.commonSpace.dto.BuildingLocationDTO;
import com.impact.brain.commonSpace.sortedData.LocationsOfBuildings;
import com.impact.brain.commonSpace.entity.*;
import com.impact.brain.commonSpace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Objects;
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
    *  is being used to avoid errors from LAZY type fetching in the
    *  attribute 'building' inside of the BuildingLocation class.
    */
    public Iterable<BuildingLocationDTO> buildingLocations() {
        ArrayList<BuildingLocationDTO> buildingLocations = new ArrayList<>();
        for(BuildingLocation buildingLocation : buildingLocationRepository.findAll()) {
            BuildingLocationDTO newLocation = new BuildingLocationDTO();
            newLocation.setId(buildingLocation.getId());
            newLocation.setBuildingId(buildingLocation.getBuilding().getId());
            newLocation.setFloorId(buildingLocation.getFloor());
            buildingLocations.add(newLocation);
        }
        return buildingLocations;
    }

    // Method to sort all locations by their corresponding buildings
    public Iterable<LocationsOfBuildings> locationsByBuilding() {
        ArrayList<LocationsOfBuildings> locationsOfBuildings = new ArrayList<>();
        for(Building currentBuilding : buildingRepository.findAll()) {
            LocationsOfBuildings locationsOfCurrBuilding = new LocationsOfBuildings();
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

    // Method to get the locations that are related to a single building
    public LocationsOfBuildings locationsOfBuilding(int buildingId){
        LocationsOfBuildings locationsOfBuilding = new LocationsOfBuildings();
        locationsOfBuilding.setBuilding(buildingRepository.findBuildingBy(buildingId));
        for(BuildingLocationDTO currentLocation : buildingLocations()) {
            if(buildingId == currentLocation.getBuildingId()) {
                locationsOfBuilding.getLocations().add(currentLocation);
            }
        }
        return locationsOfBuilding;
    }

    /* Save methods for each of the classes that have relationships with the Space class */
    public void saveSpaceType(SpaceType spaceType) {
        spaceTypeRepository.save(spaceType);
        System.out.println("Saving spaceType: " + spaceType);
    }

    public void saveSpace(Space space) {
        spaceRepository.save(space);
        System.out.println("Saving space: " + space);
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
