package com.impact.core.module.space_equipment.controller;

import com.impact.core.module.space_equipment.payload.request.SpaceEquipmentRequest;
import com.impact.core.module.space_equipment.payload.response.SpaceEquipmentResponse;
import com.impact.core.module.space_equipment.service.SpaceEquipmentService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/space-equipment")
@RequiredArgsConstructor
public class SpaceEquipmentController {

    public final SpaceEquipmentService spaceEquipmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<SpaceEquipmentResponse>>> getAllSpaceEquipments() {
        return ResponseEntity.ok(ResponseWrapper.<List<SpaceEquipmentResponse>>builder()
                .message("Listado de equipos de espacio.")
                .data(spaceEquipmentService.findAll())
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SpaceEquipmentResponse>> saveSpaceEquipment(@Valid @RequestBody SpaceEquipmentRequest spaceEquipmentRequest) {
        SpaceEquipmentResponse spaceEquipmentResponse = spaceEquipmentService.save(spaceEquipmentRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<SpaceEquipmentResponse>builder()
                .message("Equipo de espacio creado exitosamente.")
                .data(spaceEquipmentResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SpaceEquipmentResponse>> updateSpaceEquipment(@PathVariable int id, @Valid @RequestBody SpaceEquipmentRequest spaceEquipmentRequest) {
        SpaceEquipmentResponse spaceEquipmentResponse = spaceEquipmentService.update(id, spaceEquipmentRequest);

        return ResponseEntity.ok(ResponseWrapper.<SpaceEquipmentResponse>builder()
                .message("Equipo de espacio actualizado exitosamente.")
                .data(spaceEquipmentResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SpaceEquipmentResponse>> deleteSpaceEquipment(@PathVariable int id) {
        SpaceEquipmentResponse spaceEquipmentResponse = spaceEquipmentService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<SpaceEquipmentResponse>builder()
                .message("Equipo de espacio eliminado exitosamente.")
                .data(spaceEquipmentResponse)
                .build());
    }
}
