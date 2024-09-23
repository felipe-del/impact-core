package com.impact.brain.space.service;

import com.impact.brain.commonSpace.dto.BuildingLocationDTO;
import com.impact.brain.commonSpace.entity.Building;
import com.impact.brain.commonSpace.entity.BuildingLocation;
import com.impact.brain.commonSpace.entity.Space;
import com.impact.brain.commonSpace.entity.SpaceStatus;
import com.impact.brain.commonSpace.repository.*;
import com.impact.brain.commonSpace.service.SpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpaceServiceTest {
    @Mock
    private SpaceRepository spaceRepository;

    @Mock
    private SpaceStatusRepository spaceStatusRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private BuildingLocationRepository buildingLocationRepository;

    @InjectMocks
    private SpaceService spaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBuildingById_success() {
        int buildingId = 1;
        Building mockBuilding = new Building();
        mockBuilding.setName("Test Building");
        mockBuilding.setId(buildingId);

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(mockBuilding));

        Optional<Building> foundBuilding = spaceService.buildingById(buildingId);

        assertTrue(foundBuilding.isPresent());
        assertEquals(buildingId, foundBuilding.get().getId());
    }

    @Test
    void testFindBuildingById_failure() {
        int buildingId = 1;
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.empty());

        Optional<Building> foundBuilding = spaceService.buildingById(buildingId);

        assertFalse(foundBuilding.isPresent());
    }

    @Test
    void testCreateBuilding() {
        int testId = 1;
        Building newBuilding = new Building();
        newBuilding.setName("Test Building");
        newBuilding.setId(testId);

        when(buildingRepository.save(newBuilding)).thenReturn(newBuilding);

        Building createdBuilding = spaceService.saveBuilding(newBuilding);

        assertNotNull(createdBuilding);
        assertEquals("Test Building", createdBuilding.getName());
        assertEquals(testId, createdBuilding.getId());
    }

    @Test
    void testFindBuildingLocationById_success() {
        int locationId = 1;
        BuildingLocation mockLocation = new BuildingLocation();
        mockLocation.setId(locationId);

        when(buildingLocationRepository.findById(locationId)).thenReturn(Optional.of(mockLocation));

        Optional<BuildingLocation> foundLocation = spaceService.buildingLocationById(locationId);

        assertTrue(foundLocation.isPresent());
        assertEquals(locationId, foundLocation.get().getId());
    }

    @Test
    void testFindBuildingLocationById_failure() {
        int locationId = 1;
        when(buildingLocationRepository.findById(locationId)).thenReturn(Optional.empty());

        Optional<BuildingLocation> foundLocation = spaceService.buildingLocationById(locationId);

        assertFalse(foundLocation.isPresent());
    }

    @Test
    void testCreateBuildingLocation() {
        int testId = 1;
        Building mockBuilding = new Building();
        mockBuilding.setName("Test Building");
        mockBuilding.setId(testId);

        BuildingLocation mockLocation = new BuildingLocation();
        mockLocation.setId(testId);
        mockLocation.setFloor("Piso 1");
        mockLocation.setBuilding(mockBuilding);

        when(buildingLocationRepository.save(mockLocation)).thenReturn(mockLocation);

        BuildingLocation createdLocation = spaceService.saveBuildingLocation(mockLocation);

        assertNotNull(createdLocation);
        assertEquals(testId, createdLocation.getId());
        assertEquals("Piso 1", createdLocation.getFloor());

        assertEquals(testId, createdLocation.getBuilding().getId());
        assertEquals("Test Building", createdLocation.getBuilding().getName());
    }

    @Test
    void testFindStatusById_success() {
        int statusId = 1;
        SpaceStatus mockStatus = new SpaceStatus();
        mockStatus.setId(statusId);
        mockStatus.setName("Test Status");
        mockStatus.setDescription("Test Description");

        when(spaceStatusRepository.findById(statusId)).thenReturn(Optional.of(mockStatus));

        Optional<SpaceStatus> foundStatus = spaceService.spaceStatusById(statusId);

        assertTrue(foundStatus.isPresent());
        assertEquals(statusId, foundStatus.get().getId());
        assertEquals("Test Status", foundStatus.get().getName());
        assertEquals("Test Description", foundStatus.get().getDescription());
    }

    @Test
    void testFindStatusById_failure() {
        int statusId = 1;
        when(spaceStatusRepository.findById(statusId)).thenReturn(Optional.empty());

        Optional<SpaceStatus> foundStatus = spaceService.spaceStatusById(statusId);

        assertFalse(foundStatus.isPresent());
    }

    @Test
    void testCreateSpace() {
        int testId = 1;
        int testCode = 10;
        SpaceStatus mockStatus = new SpaceStatus();
        mockStatus.setId(testId);
        mockStatus.setName("Test Status");

        BuildingLocation mockLocation = new BuildingLocation();
        mockLocation.setId(testId);
        mockLocation.setFloor("Piso 1");
        mockLocation.setBuilding(new Building());

        Space mockSpace = new Space();
        mockSpace.setId(testId);
        mockSpace.setName("Test Space");
        mockSpace.setLocation(mockLocation);
        mockSpace.setStatus(mockStatus);
        mockSpace.setMaxPeople(30);
        mockSpace.setSpaceCode(testCode);
        mockSpace.setIsDeleted(false);

        when(spaceRepository.save(mockSpace)).thenReturn(mockSpace);

        Space createdSpace = spaceService.saveSpace(mockSpace);

        assertNotNull(createdSpace);
        assertEquals(testId, createdSpace.getId());
        assertEquals("Test Space", createdSpace.getName());
        assertEquals(mockLocation.getId(), createdSpace.getLocation().getId());
        assertEquals(mockLocation.getFloor(), createdSpace.getLocation().getFloor());
        assertEquals(mockStatus.getId(), createdSpace.getStatus().getId());
        assertEquals(mockStatus.getName(), createdSpace.getStatus().getName());
        assertEquals(30, createdSpace.getMaxPeople());
        assertEquals(testCode, createdSpace.getSpaceCode());
        assertEquals(false, createdSpace.getIsDeleted());
    }

    @Test
    void testAllBuildings(){
        Iterable<Building> mockBuildings = mock(Iterable.class);
        when(buildingRepository.findAll()).thenReturn(mockBuildings);

        Iterable<Building> foundBuidlings = spaceService.buildings();

        assertNotNull(foundBuidlings);
        verify(buildingRepository, times(1)).findAll();
    }

    @Test
    void testAllBuildingLocations() {
        // Arrange
        int testId1 = 1;
        int testId2 = 2;

        Building mockBuilding1 = new Building();
        mockBuilding1.setId(100);
        mockBuilding1.setName("Building 1");

        Building mockBuilding2 = new Building();
        mockBuilding2.setId(200);
        mockBuilding2.setName("Building 2");

        BuildingLocation mockLocation1 = new BuildingLocation();
        mockLocation1.setId(testId1);
        mockLocation1.setFloor("Piso 1");
        mockLocation1.setBuilding(mockBuilding1); // Associate with Building 1

        BuildingLocation mockLocation2 = new BuildingLocation();
        mockLocation2.setId(testId2);
        mockLocation2.setFloor("Piso 2");
        mockLocation2.setBuilding(mockBuilding2); // Associate with Building 2

        Iterable<BuildingLocation> mockBuildingLocations = Arrays.asList(mockLocation1, mockLocation2);
        when(buildingLocationRepository.findAll()).thenReturn(mockBuildingLocations);

        Iterable<BuildingLocationDTO> foundBuildingLocations = spaceService.buildingLocations();
        Iterator<BuildingLocationDTO> iterator = foundBuildingLocations.iterator();

        // First DTO
        assertTrue(iterator.hasNext());
        BuildingLocationDTO dto1 = iterator.next();
        assertEquals(testId1, dto1.getId());
        assertEquals("Piso 1", dto1.getFloorId());
        assertEquals(100, dto1.getBuildingId());

        // Second DTO
        assertTrue(iterator.hasNext());
        BuildingLocationDTO dto2 = iterator.next();
        assertEquals(testId2, dto2.getId());
        assertEquals("Piso 2", dto2.getFloorId());
        assertEquals(200, dto2.getBuildingId());

        assertFalse(iterator.hasNext());

        verify(buildingLocationRepository, times(1)).findAll();
    }

    @Test
    void testAllSpaces(){
        Iterable<Space> mockSpaces = mock(Iterable.class);
        when(spaceRepository.findAll()).thenReturn(mockSpaces);

        Iterable<Space> foundSpaces = spaceService.spaces();

        assertNotNull(foundSpaces);
        verify(spaceRepository, times(1)).findAll();
    }
}
