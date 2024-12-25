package com.impact.core.module.supplier.controller;

import com.impact.core.module.supplier.payload.request.SupplierRequest;
import com.impact.core.module.supplier.entity.Supplier;
import com.impact.core.module.supplier.service.SupplierService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    public final SupplierService supplierService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<SupplierRequest>> saveSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        Supplier supplier = supplierService.save(supplierRequest);
        SupplierRequest supplierRequestResponse = supplierService.toDTO(supplier);

        // TODO: RETURN A SUPPLIER RESPONSE

        return ResponseEntity.ok(ApiResponse.<SupplierRequest>builder()
                .message("Proveedor guardado.")
                .data(supplierRequestResponse)
                .build());
    }
}
