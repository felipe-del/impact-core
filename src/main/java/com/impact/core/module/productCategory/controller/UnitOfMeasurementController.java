package com.impact.core.module.productCategory.controller;

import com.impact.core.module.productCategory.payload.response.UnitOfMeasurementResponse;
import com.impact.core.module.productCategory.service.UnitOfMeasurementService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/unit-of-measurement")
@RequiredArgsConstructor
public class UnitOfMeasurementController {
    public final UnitOfMeasurementService unitOfMeasurementService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<UnitOfMeasurementResponse>>> getAllUnitOfMeasurements() {
        List<UnitOfMeasurementResponse> unitOfMeasurementResponses = unitOfMeasurementService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<UnitOfMeasurementResponse>>builder()
                .message("Lista de unidades de medida.")
                .data(unitOfMeasurementResponses)
                .build());
    }
}
