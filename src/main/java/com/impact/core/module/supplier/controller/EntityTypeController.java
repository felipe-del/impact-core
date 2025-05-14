package com.impact.core.module.supplier.controller;

import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.payload.response.EntityTypeResponse;
import com.impact.core.module.supplier.service.EntityTypeService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for managing {@link EntityType} entities.
 * <p>
 * This controller handles requests related to the {@link EntityType}, such as retrieving a list of all entity types.
 */
@RestController
@RequestMapping("/api/entity-type")
@RequiredArgsConstructor
public class EntityTypeController {
    public final EntityTypeService entityTypeService;

    /**
     * Retrieves all {@link EntityTypeResponse} DTOs.
     * <p>
     * This method returns a list of all {@link EntityTypeResponse} DTOs. Only users with the roles 'ADMINISTRATOR' or 'MANAGER'
     * are authorized to access this endpoint.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link EntityTypeResponse} DTOs.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<EntityTypeResponse>>> getAllEntityTypes() {
        List<EntityTypeResponse> entityTypeResponses = entityTypeService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<EntityTypeResponse>>builder()
                .message("Lista de tipos de entidad.")
                .data(entityTypeResponses)
                .build());
    }
}
