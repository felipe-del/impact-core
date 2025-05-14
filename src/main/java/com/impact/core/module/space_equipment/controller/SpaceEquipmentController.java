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

/**
 * Controller for handling operations related to space equipment.
 * <p>
 * Provides endpoints to list, create, update, and delete space equipment entries.
 * Access to these endpoints is restricted to users with administrator or manager roles.
 */
@RestController
@RequestMapping("api/space-equipment")
@RequiredArgsConstructor
public class SpaceEquipmentController {

    public final SpaceEquipmentService spaceEquipmentService;

    /**
     * Retrieves a list of all space equipment entries.
     * Only accessible to users with administrator or manager roles.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper}
     * with a list of {@link SpaceEquipmentResponse} objects.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<SpaceEquipmentResponse>>> getAllSpaceEquipments() {
        return ResponseEntity.ok(ResponseWrapper.<List<SpaceEquipmentResponse>>builder()
                .message("Listado de equipos de espacio.")
                .data(spaceEquipmentService.findAll())
                .build());
    }

    /**
     * Creates a new space equipment entry.
     * Only accessible to users with administrator or manager roles.
     *
     * @param spaceEquipmentRequest the {@link SpaceEquipmentRequest} containing the data to create.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper}
     * with the created {@link SpaceEquipmentResponse} object.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SpaceEquipmentResponse>> saveSpaceEquipment(@Valid @RequestBody SpaceEquipmentRequest spaceEquipmentRequest) {
        SpaceEquipmentResponse spaceEquipmentResponse = spaceEquipmentService.save(spaceEquipmentRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<SpaceEquipmentResponse>builder()
                .message("Equipo de espacio creado exitosamente.")
                .data(spaceEquipmentResponse)
                .build());
    }

    /**
     * Updates an existing space equipment entry.
     * Only accessible to users with administrator or manager roles.
     *
     * @param id the unique identifier of the space equipment to update.
     * @param spaceEquipmentRequest the {@link SpaceEquipmentRequest} containing updated data.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper}
     * with the updated {@link SpaceEquipmentResponse} object.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SpaceEquipmentResponse>> updateSpaceEquipment(@PathVariable int id, @Valid @RequestBody SpaceEquipmentRequest spaceEquipmentRequest) {
        SpaceEquipmentResponse spaceEquipmentResponse = spaceEquipmentService.update(id, spaceEquipmentRequest);

        return ResponseEntity.ok(ResponseWrapper.<SpaceEquipmentResponse>builder()
                .message("Equipo de espacio actualizado exitosamente.")
                .data(spaceEquipmentResponse)
                .build());
    }

    /**
     * Deletes a space equipment entry by its identifier.
     * Only accessible to users with administrator or manager roles.
     *
     * @param id the unique identifier of the space equipment to delete.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper}
     * with the deleted {@link SpaceEquipmentResponse} object.
     */
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
