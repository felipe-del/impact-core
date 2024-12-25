package com.impact.core.module.supplier.controller;

import com.impact.core.module.supplier.mapper.EntityTypeMapper;
import com.impact.core.module.supplier.payload.response.EntityTypeResponse;
import com.impact.core.module.supplier.service.EntityTypeService;
import com.impact.core.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/entity-type")
@RequiredArgsConstructor
public class EntityTypeController {
    public final EntityTypeService entityTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<EntityTypeResponse>>> getAllEntityTypes() {
        List<EntityTypeResponse> entityTypeResponses = entityTypeService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<EntityTypeResponse>>builder()
                .message("Lista de tipos de entidad.")
                .data(entityTypeResponses)
                .build());
    }
}
