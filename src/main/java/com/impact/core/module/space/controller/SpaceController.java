package com.impact.core.module.space.controller;

import com.impact.core.module.space.payload.request.SpaceRequest;
import com.impact.core.module.space.payload.response.SpaceResponse;
import com.impact.core.module.space.service.SpaceService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that handles operations related to spaces within the system.
 * <p>
 * This controller provides endpoints to create, retrieve, update, and delete space records.
 * Access is restricted based on roles: Administrator, Manager, and Teacher.
 */
@RestController
@RequestMapping("api/space")
@RequiredArgsConstructor
public class SpaceController {
    public final SpaceService spaceService;

    /**
     * Retrieves a list of all spaces.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link SpaceResponse}.
     * Accessible by users with roles: Administrator, Manager, or Teacher.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceResponse>>> getAllSpaces() {
        return ResponseEntity.ok(ResponseWrapper.<List<SpaceResponse>>builder()
                .message("Listado de espacios.")
                .data(spaceService.findAll())
                .build());
    }

    /**
     * Creates a new space based on the provided data.
     *
     * @param spaceRequest the request body containing space details.
     * @return a {@link ResponseEntity} with a {@link ResponseWrapper} containing the created {@link SpaceResponse}.
     * Accessible by users with roles: Administrator or Manager.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SpaceResponse>> saveSpace(@Valid @RequestBody SpaceRequest spaceRequest) {
        SpaceResponse spaceResponse = spaceService.save(spaceRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<SpaceResponse>builder()
                .message("Espacio creado exitosamente.")
                .data(spaceResponse)
                .build());
    }

    /**
     * Updates an existing space by its identifier.
     *
     * @param id the identifier of the space to update.
     * @param spaceRequest the request body containing the updated space data.
     * @return a {@link ResponseEntity} with a {@link ResponseWrapper} containing the updated {@link SpaceResponse}.
     * Accessible by users with roles: Administrator or Manager.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SpaceResponse>> updateSpace(@PathVariable int id, @Valid @RequestBody SpaceRequest spaceRequest) {
        SpaceResponse spaceResponse = spaceService.update(id, spaceRequest);

        return ResponseEntity.ok(ResponseWrapper.<SpaceResponse>builder()
                .message("Espacio actualizado exitosamente.")
                .data(spaceResponse)
                .build());
    }

    /**
     * Deletes a space by its identifier.
     *
     * @param id the identifier of the space to delete.
     * @return a {@link ResponseEntity} with a {@link ResponseWrapper} containing the deleted {@link SpaceResponse}.
     * Accessible by users with roles: Administrator or Manager.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SpaceResponse>> deleteSpace(@PathVariable int id) {
        SpaceResponse spaceResponse = spaceService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<SpaceResponse>builder()
                .message("Espacio eliminado exitosamente.")
                .data(spaceResponse)
                .build());
    }

}
