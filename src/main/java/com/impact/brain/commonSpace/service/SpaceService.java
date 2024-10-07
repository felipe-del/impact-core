package com.impact.brain.commonSpace.service;

import com.impact.brain.brand.entity.Brand;
import com.impact.brain.brand.repository.BrandRepository;
import com.impact.brain.commonSpace.dto.*;
import com.impact.brain.commonSpace.entity.*;
import com.impact.brain.commonSpace.repository.*;
import com.impact.brain.user.entity.User;
import com.impact.brain.request.entity.Request;
import com.impact.brain.request.entity.RequestStatus;
import com.impact.brain.request.entity.ResourceRequestStatus;
import com.impact.brain.request.repository.RequestRepository;
import com.impact.brain.request.repository.RequestStatusRepository;
import com.impact.brain.request.repository.ResourceRequestStatusRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@org.springframework.stereotype.Service("spaceService")
public class SpaceService {
    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SpaceStatusRepository spaceStatusRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingLocationRepository buildingLocationRepository;

    @Autowired
    private SpaceEquipmentRepository spaceEquipmentRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @Autowired
    private ResourceRequestStatusRepository resourceRequestStatusRepository;

    @Autowired
    private SpaceRequestRepository spaceRequestRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SpaceReservationRepository spaceReservationRepository;

    /* FindAll() methods to retrieve the entire list of each object type respectively  */
    public Iterable<Brand> brands() { return brandRepository.findAll(); }
    public Iterable<SpaceStatus> spaceStatuses() { return spaceStatusRepository.findAll(); }
    public Iterable<Building> buildings() { return buildingRepository.findAll(); }
    public Iterable<Space> spaces() { return spaceRepository.findAll(); }
    public Iterable<Request> requests() { return requestRepository.findAll(); }
    public Iterable<RequestStatus> requestStatuses() { return requestStatusRepository.findAll(); }
    public Iterable<ResourceRequestStatus> resourceRequests() { return resourceRequestStatusRepository.findAll(); }
    public Iterable<SpaceRequest> spaceRequests() { return spaceRequestRepository.findAll(); }
    public Iterable<SpaceReservation> spaceReservations() { return spaceReservationRepository.findAll(); }

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

    private SpaceEquipmentDTO spaceEquipmentToDTO(SpaceEquipment spaceEquipment){
        SpaceEquipmentDTO spaceEquipmentDTO = new SpaceEquipmentDTO();

        spaceEquipmentDTO.setId(spaceEquipment.getId());
        spaceEquipmentDTO.setName(spaceEquipment.getName());
        spaceEquipmentDTO.setBrandId(spaceEquipment.getBrand().getId());
        spaceEquipmentDTO.setSpaceId(spaceEquipment.getSpace().getId());

        spaceEquipmentDTO.setQuantity(spaceEquipment.getQuantity());

        return spaceEquipmentDTO;
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

    private Pair<SpaceRequest, SpaceReservation> spaceRequestInformationToSpaceReqAndRes(SpaceRequestInformationDTO newSpaceRequestData, User userData){
        SpaceRequest newSpaceRequest = new SpaceRequest();
        SpaceReservation newSpaceReservation = new SpaceReservation();

        // Request Information
        newSpaceRequest.setId(0);
        newSpaceRequest.setRequest(makingRequest(userData));
        newSpaceRequest.setNumPeople(newSpaceRequestData.getNumPeople());
        newSpaceRequest.setEventDesc(newSpaceRequestData.getEventDesc());
        newSpaceRequest.setUseEquipment(newSpaceRequestData.getUseEquipment());

        Optional<ResourceRequestStatus> resourceRequest = resourceRequestStatusById(newSpaceRequestData.getStatusId());
        resourceRequest.ifPresent(newSpaceRequest::setStatus);

        // Reservation Info
        newSpaceReservation.setId(0);
        newSpaceReservation.setStartTime(newSpaceRequestData.getStartTime());
        newSpaceReservation.setEndTime(newSpaceRequestData.getEndTime());

        // Shared information
        Optional<Space> space = spaceById(newSpaceRequestData.getSpaceId());
        space.ifPresent(newSpaceRequest::setSpace);
        space.ifPresent(newSpaceReservation::setSpace);

        return new Pair<>(newSpaceRequest, newSpaceReservation);
    }

    public SpaceEquipment spaceEquipmentDTOToEquipment(SpaceEquipmentDTO dto){
        SpaceEquipment spaceEquipment = new SpaceEquipment();

        spaceEquipment.setName(dto.getName());
        spaceEquipment.setQuantity(dto.getQuantity());
        spaceEquipment.setId(dto.getId());

        Optional<Brand> brand = brandRepository.findById(dto.getBrandId());
        brand.ifPresent(spaceEquipment::setBrand);

        Space space = spaceRepository.findSpaceById(dto.getSpaceId());
        if (space != null){ spaceEquipment.setSpace(space); }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return spaceEquipment;
    }

    /* Method to make requests */
    private Request makingRequest(User user){
        Request request = new Request();
        request.setId(0);
        request.setDate(LocalDate.now());
        request.setUser(user);

        Optional<RequestStatus> requestStatus = requestStatusRepository.findById(1);
        requestStatus.ifPresent(request::setStatus);

        return requestRepository.save(request);
    }

    // --------------------------------------------------------------------------------------------------------------------

    public Iterable<BuildingLocationDTO> buildingLocations() {
        ArrayList<BuildingLocationDTO> buildingLocations = new ArrayList<>();
        for(BuildingLocation buildingLocation : buildingLocationRepository.findAll()) {
            buildingLocations.add(buildingLocationToDTO(buildingLocation));
        }
        return buildingLocations;
    }

    public Iterable<SpaceEquipmentDTO> spaceEquipments(){
        ArrayList<SpaceEquipmentDTO> spaceEquipments = new ArrayList<>();
        for(SpaceEquipment spaceEquipment : spaceEquipmentRepository.findAll()) {
            spaceEquipments.add(spaceEquipmentToDTO(spaceEquipment));
        }
        return spaceEquipments;
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

    public Iterable<SpaceEquipmentSortedDTO> equipmentBySpace() {
        ArrayList<SpaceEquipmentSortedDTO> equipments = new ArrayList<>();

        for(Space space : spaceRepository.findAll()) {

            SpaceEquipmentSortedDTO equipment = new SpaceEquipmentSortedDTO();
            equipment.setSpace(space);

            for(SpaceEquipmentDTO sEqu : spaceEquipments()){
                if(space.getId() == sEqu.getSpaceId()){
                    equipment.getEquipment().add(sEqu);
                }
            }

            equipments.add(equipment);
        }

        return equipments;
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

    public SpaceEquipment saveSpaceEquipment(SpaceEquipmentDTO spaceEquipment) {
        return spaceEquipmentRepository.save(spaceEquipmentDTOToEquipment(spaceEquipment));
    }

    public Pair<SpaceRequest, SpaceReservation> saveSpaceRequestAndReservation(SpaceRequestInformationDTO spaceRequestInformation, User userData) {
        Pair<SpaceRequest, SpaceReservation> savedRequest = spaceRequestInformationToSpaceReqAndRes(spaceRequestInformation, userData);
        spaceRequestRepository.save(savedRequest.a);
        spaceReservationRepository.save(savedRequest.b);
        return savedRequest;
    }

    /* Methods to get objects */
    public Optional<Space> spaceById(int id) { return spaceRepository.findById(id); }
    public Optional<SpaceStatus> spaceStatusById(int id) { return spaceStatusRepository.findById(id); }
    public Optional<Building> buildingById(int id) { return buildingRepository.findById(id); }
    public Optional<BuildingLocation> buildingLocationById(int id) { return buildingLocationRepository.findById(id); }
    public Optional<Request> requestById(int id) { return requestRepository.findById(id); }
    public Optional<RequestStatus> requestStatusById(int id) { return requestStatusRepository.findById(id); }
    public Optional<ResourceRequestStatus> resourceRequestStatusById(int id) { return resourceRequestStatusRepository.findById(id); }
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
