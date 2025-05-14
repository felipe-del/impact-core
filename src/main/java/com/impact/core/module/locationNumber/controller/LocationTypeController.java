package com.impact.core.module.locationNumber.controller;

import com.impact.core.module.locationNumber.entity.LocationType;
import com.impact.core.module.locationNumber.payload.request.LocationTypeRequest;
import com.impact.core.module.locationNumber.payload.response.LocationTypeResponse;
import com.impact.core.module.locationNumber.service.LocationTypeService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link LocationType} resources.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting {@link LocationType} entities.
 * It ensures that only users with specific roles (e.g., ADMINISTRATOR or MANAGER) have access to these endpoints.
 * </p>
 */
@RestController
@RequestMapping("/api/location-type")
@RequiredArgsConstructor
public class LocationTypeController {
    public final LocationTypeService locationTypeService;


    /**
     * Retrieves a list of all {@link LocationType} entities.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link LocationTypeResponse} DTOs.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<LocationTypeResponse>>> getAllLocationTypes() {
        List<LocationTypeResponse> locationTypeResponses = locationTypeService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<LocationTypeResponse>>builder()
                .message("Lista de tipos de ubicación.")
                .data(locationTypeResponses)
                .build());
    }

    /**
     * Creates a new {@link LocationType} entity.
     *
     * @param locationTypeRequest the {@link LocationTypeRequest} DTO containing the data for the new entity.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the created {@link LocationTypeResponse} DTO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<LocationTypeResponse>> saveLocationType(@Valid @RequestBody LocationTypeRequest locationTypeRequest) {
        LocationTypeResponse locationTypeResponse = locationTypeService.save(locationTypeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<LocationTypeResponse>builder()
                .message("Tipo de ubicación guardado.")
                .data(locationTypeResponse)
                .build());
    }

    /**
     * Updates an existing {@link LocationType} entity by its ID.
     *
     * @param id the ID of the {@link LocationType} to be updated.
     * @param locationTypeRequest the {@link LocationTypeRequest} DTO containing the updated data.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the updated {@link LocationTypeResponse} DTO.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<LocationTypeResponse>> updateLocationType(@PathVariable int id, @Valid @RequestBody LocationTypeRequest locationTypeRequest) {
        LocationTypeResponse locationTypeResponse = locationTypeService.update(id, locationTypeRequest);

        return ResponseEntity.ok(ResponseWrapper.<LocationTypeResponse>builder()
                .message("Tipo de ubicación actualizado.")
                .data(locationTypeResponse)
                .build());
    }

    /**
     * Deletes an existing {@link LocationType} entity by its ID.
     *
     * @param id the ID of the {@link LocationType} to be deleted.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the deleted {@link LocationTypeResponse} DTO.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<LocationTypeResponse>> deleteLocationType(@PathVariable int id) {
        LocationTypeResponse locationTypeResponse = locationTypeService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<LocationTypeResponse>builder()
                .message("Tipo de ubicación eliminado.")
                .data(locationTypeResponse)
                .build());
    }

}
