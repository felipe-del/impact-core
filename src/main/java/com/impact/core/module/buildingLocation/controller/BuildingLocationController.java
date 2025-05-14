package com.impact.core.module.buildingLocation.controller;

import com.impact.core.module.buildingLocation.entity.BuildingLocation;
import com.impact.core.module.buildingLocation.payload.request.BuildingLocationRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingLocationResponse;
import com.impact.core.module.buildingLocation.service.BuildingLocationService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link BuildingLocation} entities.
 * <p>
 * Provides endpoints to retrieve, create, update, and delete building locations.
 * Each endpoint returns a standardized {@link ResponseWrapper} containing a {@link BuildingLocationResponse}
 * Data Transfer Object (DTO) or a list of them.
 * <p>
 * Access is restricted to users with roles {@code ADMINISTRATOR} or {@code MANAGER}.
 */
@RestController
@RequestMapping("api/building-location")
@RequiredArgsConstructor
public class BuildingLocationController {
    public final BuildingLocationService buildingLocationService;

    /**
     * Retrieves all {@link BuildingLocation} entities as {@link BuildingLocationResponse}
     * Data Transfer Objects (DTOs), wrapped in a {@link ResponseWrapper}.
     *
     * @return the HTTP response containing the wrapped list of building location DTOs
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<BuildingLocationResponse>>> getAllBuildingLocations() {
        List<BuildingLocationResponse> buildingLocationResponses = buildingLocationService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<BuildingLocationResponse>>builder()
                .message("Lista de ubicaciones de edificio.")
                .data(buildingLocationResponses)
                .build());
    }

    /**
     * Saves a new {@link BuildingLocation} based on the given {@link BuildingLocationRequest}
     * Data Transfer Object (DTO), and returns the result as a {@link BuildingLocationResponse}.
     *
     * @param buildingLocationRequest the request DTO containing building location data
     * @return the HTTP response containing the created building location DTO
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BuildingLocationResponse>> saveBuildingLocation(@Valid @RequestBody BuildingLocationRequest buildingLocationRequest) {
        BuildingLocationResponse buildingLocationResponse = buildingLocationService.save(buildingLocationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<BuildingLocationResponse>builder()
                .message("Ubicación de edificio guardada.")
                .data(buildingLocationResponse)
                .build());
    }

    /**
     * Updates an existing {@link BuildingLocation} identified by its ID, using the data provided
     * in the {@link BuildingLocationRequest} Data Transfer Object (DTO), and returns the updated result
     * as a {@link BuildingLocationResponse}.
     *
     * @param id the identifier of the building location to update
     * @param buildingLocationRequest the request DTO containing updated data
     * @return the HTTP response containing the updated building location DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BuildingLocationResponse>> updateBuildingLocation(@PathVariable Integer id, @Valid @RequestBody BuildingLocationRequest buildingLocationRequest) {
        BuildingLocationResponse buildingLocationResponse = buildingLocationService.update(id, buildingLocationRequest);

        return ResponseEntity.ok(ResponseWrapper.<BuildingLocationResponse>builder()
                .message("Ubicación de edificio actualizada.")
                .data(buildingLocationResponse)
                .build());
    }

    /**
     * Deletes the {@link BuildingLocation} identified by its ID and returns the deleted entity
     * as a {@link BuildingLocationResponse} Data Transfer Object (DTO).
     *
     * @param id the identifier of the building location to delete
     * @return the HTTP response containing the deleted building location DTO
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BuildingLocationResponse>> deleteBuildingLocation(@PathVariable Integer id) {
        BuildingLocationResponse buildingLocationResponse = buildingLocationService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<BuildingLocationResponse>builder()
                .message("Ubicación de edificio eliminada.")
                .data(buildingLocationResponse)
                .build());
    }
}
