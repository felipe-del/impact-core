package com.impact.core.module.supplier.controller;

import com.impact.core.module.supplier.payload.request.SupplierRequest;
import com.impact.core.module.supplier.entity.Supplier;
import com.impact.core.module.supplier.payload.response.SupplierResponse;
import com.impact.core.module.supplier.service.SupplierService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    public final SupplierService supplierService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<SupplierResponse>>> getAllSuppliers() {
        List<SupplierResponse> supplierResponses = supplierService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<SupplierResponse>>builder()
                .message("Lista de proveedores.")
                .data(supplierResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<SupplierResponse>> saveSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.save(supplierRequest);

        return ResponseEntity.ok(ApiResponse.<SupplierResponse>builder()
                .message("Proveedor guardado.")
                .data(supplierResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<SupplierResponse>> updateSupplier(@PathVariable Integer id, @Valid @RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.update(id, supplierRequest);

        return ResponseEntity.ok(ApiResponse.<SupplierResponse>builder()
                .message("Proveedor actualizado.")
                .data(supplierResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<SupplierResponse>> deleteSupplier(@PathVariable Integer id) {
        SupplierResponse supplierResponse = supplierService.delete(id);

        return ResponseEntity.ok(ApiResponse.<SupplierResponse>builder()
                .message("Proveedor eliminado.")
                .data(supplierResponse)
                .build());
    }

}
