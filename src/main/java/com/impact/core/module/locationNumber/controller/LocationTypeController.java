package com.impact.core.module.locationNumber.controller;

import com.impact.core.module.locationNumber.payload.request.LocationTypeRequest;
import com.impact.core.module.locationNumber.payload.response.LocationTypeResponse;
import com.impact.core.module.locationNumber.service.LocationTypeService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location-type")
@RequiredArgsConstructor
public class LocationTypeController {
    public final LocationTypeService locationTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<LocationTypeResponse>>> getAllLocationTypes() {
        List<LocationTypeResponse> locationTypeResponses = locationTypeService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<LocationTypeResponse>>builder()
                .message("Lista de tipos de ubicación.")
                .data(locationTypeResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<LocationTypeResponse>> saveLocationType(@Valid @RequestBody LocationTypeRequest locationTypeRequest) {
        LocationTypeResponse locationTypeResponse = locationTypeService.save(locationTypeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<LocationTypeResponse>builder()
                .message("Tipo de ubicación guardado.")
                .data(locationTypeResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<LocationTypeResponse>> updateLocationType(@PathVariable int id, @Valid @RequestBody LocationTypeRequest locationTypeRequest) {
        LocationTypeResponse locationTypeResponse = locationTypeService.update(id, locationTypeRequest);

        return ResponseEntity.ok(ApiResponse.<LocationTypeResponse>builder()
                .message("Tipo de ubicación actualizado.")
                .data(locationTypeResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<LocationTypeResponse>> deleteLocationType(@PathVariable int id) {
        LocationTypeResponse locationTypeResponse = locationTypeService.delete(id);

        return ResponseEntity.ok(ApiResponse.<LocationTypeResponse>builder()
                .message("Tipo de ubicación eliminado.")
                .data(locationTypeResponse)
                .build());
    }

}
