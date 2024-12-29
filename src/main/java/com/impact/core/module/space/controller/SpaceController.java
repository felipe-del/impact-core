package com.impact.core.module.space.controller;

import com.impact.core.module.space.payload.request.SpaceRequest;
import com.impact.core.module.space.payload.response.SpaceResponse;
import com.impact.core.module.space.service.SpaceService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/space")
@RequiredArgsConstructor
public class SpaceController {
    public final SpaceService spaceService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<SpaceResponse>>> getAllSpaces() {
        return ResponseEntity.ok(ApiResponse.<List<SpaceResponse>>builder()
                .message("Listado de espacios.")
                .data(spaceService.findAll())
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<SpaceResponse>> saveSpace(@Valid @RequestBody SpaceRequest spaceRequest) {
        SpaceResponse spaceResponse = spaceService.save(spaceRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<SpaceResponse>builder()
                .message("Espacio creado exitosamente.")
                .data(spaceResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<SpaceResponse>> updateSpace(@PathVariable int id, @Valid @RequestBody SpaceRequest spaceRequest) {
        SpaceResponse spaceResponse = spaceService.update(id, spaceRequest);

        return ResponseEntity.ok(ApiResponse.<SpaceResponse>builder()
                .message("Espacio actualizado exitosamente.")
                .data(spaceResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<SpaceResponse>> deleteSpace(@PathVariable int id) {
        SpaceResponse spaceResponse = spaceService.delete(id);

        return ResponseEntity.ok(ApiResponse.<SpaceResponse>builder()
                .message("Espacio eliminado exitosamente.")
                .data(spaceResponse)
                .build());
    }

}
