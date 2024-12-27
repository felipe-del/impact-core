package com.impact.core.module.buildingLocation.controller;

import com.impact.core.module.buildingLocation.payload.request.BuildingRequest;
import com.impact.core.module.buildingLocation.payload.response.BuildingResponse;
import com.impact.core.module.buildingLocation.service.BuildingService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/building")
@RequiredArgsConstructor
public class BuildingController {
    public final BuildingService buildingService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<BuildingResponse>>> getAllBuildings() {
        List<BuildingResponse> buildingResponses = buildingService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<BuildingResponse>>builder()
                .message("Lista de edificios")
                .data(buildingResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BuildingResponse>> saveBuilding(@Valid @RequestBody BuildingRequest buildingRequest) {
        BuildingResponse buildingResponse = buildingService.save(buildingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<BuildingResponse>builder()
                .message("Edificio creado exitosamente")
                .data(buildingResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BuildingResponse>> updateBuilding(@PathVariable Integer id, @Valid @RequestBody BuildingRequest buildingRequest) {
        BuildingResponse buildingResponse = buildingService.update(id, buildingRequest);

        return ResponseEntity.ok(ApiResponse.<BuildingResponse>builder()
                .message("Edificio actualizado exitosamente")
                .data(buildingResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BuildingResponse>> deleteBuilding(@PathVariable Integer id) {
        BuildingResponse buildingResponse = buildingService.delete(id);

        return ResponseEntity.ok(ApiResponse.<BuildingResponse>builder()
                .message("Edificio eliminado exitosamente")
                .data(buildingResponse)
                .build());
    }
}
