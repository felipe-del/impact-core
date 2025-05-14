package com.impact.core.module.buildingLocation.controller;

import com.impact.core.module.buildingLocation.entity.Building;
import com.impact.core.module.buildingLocation.payload.request.BuildingRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingResponse;
import com.impact.core.module.buildingLocation.service.BuildingService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link Building} entities.
 * <p>
 * Provides endpoints to retrieve, create, update, and delete buildings.
 * Each endpoint returns a standardized {@link ResponseWrapper} containing
 * a {@link BuildingResponse} or a list of them.
 * <p>
 * Only users with roles {@code ADMINISTRATOR} or {@code MANAGER} are authorized to access these endpoints.
 */
@RestController
@RequestMapping("api/building")
@RequiredArgsConstructor
public class BuildingController {
    public final BuildingService buildingService;

    /**
     * Retrieves a list of all {@link Building} entities as {@link BuildingResponse}
     * Data Transfer Objects (DTOs), wrapped in a {@link ResponseWrapper}.
     *
     * @return the HTTP response containing the wrapped list of building DTOs
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<BuildingResponse>>> getAllBuildings() {
        List<BuildingResponse> buildingResponses = buildingService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<BuildingResponse>>builder()
                .message("Lista de edificios")
                .data(buildingResponses)
                .build());
    }

    /**
     * Saves a new {@link Building} entity from the provided {@link BuildingRequest}
     * Data Transfer Object (DTO), and returns the result as a wrapped {@link BuildingResponse}.
     *
     * @param buildingRequest the input DTO representing the building to save
     * @return the HTTP response containing the created building DTO
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BuildingResponse>> saveBuilding(@Valid @RequestBody BuildingRequest buildingRequest) {
        BuildingResponse buildingResponse = buildingService.save(buildingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<BuildingResponse>builder()
                .message("Edificio creado exitosamente")
                .data(buildingResponse)
                .build());
    }

    /**
     * Updates an existing {@link Building} entity identified by its ID,
     * using the provided {@link BuildingRequest} Data Transfer Object (DTO),
     * and returns the result as a wrapped {@link BuildingResponse}.
     *
     * @param id the identifier of the building to update
     * @param buildingRequest the input DTO containing updated data
     * @return the HTTP response containing the updated building DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BuildingResponse>> updateBuilding(@PathVariable Integer id, @Valid @RequestBody BuildingRequest buildingRequest) {
        BuildingResponse buildingResponse = buildingService.update(id, buildingRequest);

        return ResponseEntity.ok(ResponseWrapper.<BuildingResponse>builder()
                .message("Edificio actualizado exitosamente")
                .data(buildingResponse)
                .build());
    }

    /**
     * Deletes the {@link Building} entity identified by its ID and returns the deleted
     * building as a wrapped {@link BuildingResponse}.
     *
     * @param id the identifier of the building to delete
     * @return the HTTP response containing the deleted building DTO
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BuildingResponse>> deleteBuilding(@PathVariable Integer id) {
        BuildingResponse buildingResponse = buildingService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<BuildingResponse>builder()
                .message("Edificio eliminado exitosamente")
                .data(buildingResponse)
                .build());
    }
}
