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
}
