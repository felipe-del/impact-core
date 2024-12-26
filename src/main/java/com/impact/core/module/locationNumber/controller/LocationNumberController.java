package com.impact.core.module.locationNumber.controller;

import com.impact.core.module.locationNumber.payload.request.LocationNumberRequest;
import com.impact.core.module.locationNumber.payload.response.LocationNumberResponse;
import com.impact.core.module.locationNumber.service.LocationNumberService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location-number")
@RequiredArgsConstructor
public class LocationNumberController {
    public final LocationNumberService locationNumberService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<LocationNumberResponse>>> getAllLocationNumbers() {
        List<LocationNumberResponse> locationNumberResponses = locationNumberService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<LocationNumberResponse>>builder()
                .message("Lista de números de ubicación.")
                .data(locationNumberResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<LocationNumberResponse>> saveLocationNumber(@Valid @RequestBody LocationNumberRequest locationNumberRequest) {
        LocationNumberResponse locationNumberResponse = locationNumberService.save(locationNumberRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<LocationNumberResponse>builder()
                .message("Número de ubicación guardado.")
                .data(locationNumberResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<LocationNumberResponse>> updateLocationNumber(@PathVariable int id ,@Valid @RequestBody LocationNumberRequest locationNumberRequest) {
        LocationNumberResponse locationNumberResponse = locationNumberService.update(id, locationNumberRequest);

        return ResponseEntity.ok(ApiResponse.<LocationNumberResponse>builder()
                .message("Número de ubicación actualizado.")
                .data(locationNumberResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<LocationNumberResponse>> deleteLocationNumber(@PathVariable int id) {
        LocationNumberResponse locationNumberResponse = locationNumberService.delete(id);

        return ResponseEntity.ok(ApiResponse.<LocationNumberResponse>builder()
                .message("Número de ubicación eliminado.")
                .data(locationNumberResponse)
                .build());
    }
}
