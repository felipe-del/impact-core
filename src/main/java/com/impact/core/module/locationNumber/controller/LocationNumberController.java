package com.impact.core.module.locationNumber.controller;

import com.impact.core.module.locationNumber.entity.LocationNumber;
import com.impact.core.module.locationNumber.payload.request.LocationNumberRequest;
import com.impact.core.module.locationNumber.payload.response.LocationNumberResponse;
import com.impact.core.module.locationNumber.service.LocationNumberService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link LocationNumber} resources.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting {@link LocationNumber} entities.
 * It ensures that only users with specific roles (e.g., ADMINISTRATOR or MANAGER) have access to these endpoints.
 * </p>
 */
@RestController
@RequestMapping("/api/location-number")
@RequiredArgsConstructor
public class LocationNumberController {
    public final LocationNumberService locationNumberService;

    /**
     * Retrieves a list of all {@link LocationNumber} entities.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link LocationNumberResponse} DTOs.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<LocationNumberResponse>>> getAllLocationNumbers() {
        List<LocationNumberResponse> locationNumberResponses = locationNumberService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<LocationNumberResponse>>builder()
                .message("Lista de números de ubicación.")
                .data(locationNumberResponses)
                .build());
    }


    /**
     * Creates a new {@link LocationNumber} entity.
     *
     * @param locationNumberRequest the {@link LocationNumberRequest} DTO containing the data for the new entity.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the created {@link LocationNumberResponse} DTO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<LocationNumberResponse>> saveLocationNumber(@Valid @RequestBody LocationNumberRequest locationNumberRequest) {
        LocationNumberResponse locationNumberResponse = locationNumberService.save(locationNumberRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<LocationNumberResponse>builder()
                .message("Número de ubicación guardado.")
                .data(locationNumberResponse)
                .build());
    }


    /**
     * Updates an existing {@link LocationNumber} entity by its ID.
     *
     * @param id the ID of the {@link LocationNumber} to be updated.
     * @param locationNumberRequest the {@link LocationNumberRequest} DTO containing the updated data.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the updated {@link LocationNumberResponse} DTO.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<LocationNumberResponse>> updateLocationNumber(@PathVariable int id , @Valid @RequestBody LocationNumberRequest locationNumberRequest) {
        LocationNumberResponse locationNumberResponse = locationNumberService.update(id, locationNumberRequest);

        return ResponseEntity.ok(ResponseWrapper.<LocationNumberResponse>builder()
                .message("Número de ubicación actualizado.")
                .data(locationNumberResponse)
                .build());
    }

    /**
     * Deletes an existing {@link LocationNumber} entity by its ID.
     *
     * @param id the ID of the {@link LocationNumber} to be deleted.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the deleted {@link LocationNumberResponse} DTO.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<LocationNumberResponse>> deleteLocationNumber(@PathVariable int id) {
        LocationNumberResponse locationNumberResponse = locationNumberService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<LocationNumberResponse>builder()
                .message("Número de ubicación eliminado.")
                .data(locationNumberResponse)
                .build());
    }
}
