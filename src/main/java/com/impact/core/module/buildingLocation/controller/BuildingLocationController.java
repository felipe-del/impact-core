package com.impact.core.module.buildingLocation.controller;

import com.impact.core.module.buildingLocation.payload.request.BuildingLocationRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingLocationResponse;
import com.impact.core.module.buildingLocation.service.BuildingLocationService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/building-location")
@RequiredArgsConstructor
public class BuildingLocationController {
    public final BuildingLocationService buildingLocationService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<BuildingLocationResponse>>> getAllBuildingLocations() {
        List<BuildingLocationResponse> buildingLocationResponses = buildingLocationService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<BuildingLocationResponse>>builder()
                .message("Lista de ubicaciones de edificio.")
                .data(buildingLocationResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BuildingLocationResponse>> saveBuildingLocation(@Valid @RequestBody BuildingLocationRequest buildingLocationRequest) {
        BuildingLocationResponse buildingLocationResponse = buildingLocationService.save(buildingLocationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<BuildingLocationResponse>builder()
                .message("Ubicación de edificio guardada.")
                .data(buildingLocationResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BuildingLocationResponse>> updateBuildingLocation(@PathVariable Integer id, @Valid @RequestBody BuildingLocationRequest buildingLocationRequest) {
        BuildingLocationResponse buildingLocationResponse = buildingLocationService.update(id, buildingLocationRequest);

        return ResponseEntity.ok(ApiResponse.<BuildingLocationResponse>builder()
                .message("Ubicación de edificio actualizada.")
                .data(buildingLocationResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BuildingLocationResponse>> deleteBuildingLocation(@PathVariable Integer id) {
        BuildingLocationResponse buildingLocationResponse = buildingLocationService.delete(id);

        return ResponseEntity.ok(ApiResponse.<BuildingLocationResponse>builder()
                .message("Ubicación de edificio eliminada.")
                .data(buildingLocationResponse)
                .build());
    }
}
