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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<SupplierResponse>>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.findAll();
        List<SupplierResponse> supplierResponses = suppliers.stream()
                .map(supplierService::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.<List<SupplierResponse>>builder()
                .message("Lista de proveedores.")
                .data(supplierResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<SupplierResponse>> saveSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        Supplier supplier = supplierService.save(supplierRequest);
        SupplierResponse supplierResponse = supplierService.toDTO(supplier);

        return ResponseEntity.ok(ApiResponse.<SupplierResponse>builder()
                .message("Proveedor guardado.")
                .data(supplierResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<SupplierResponse>> updateSupplier(@PathVariable Integer id, @Valid @RequestBody SupplierRequest supplierRequest) {
        Supplier supplier = supplierService.update(id, supplierRequest);
        SupplierResponse supplierResponse = supplierService.toDTO(supplier);

        return ResponseEntity.ok(ApiResponse.<SupplierResponse>builder()
                .message("Proveedor actualizado.")
                .data(supplierResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<SupplierResponse>> deleteSupplier(@PathVariable Integer id) {
        Supplier supplier = supplierService.delete(id);
        SupplierResponse supplierResponse = supplierService.toDTO(supplier);

        return ResponseEntity.ok(ApiResponse.<SupplierResponse>builder()
                .message("Proveedor eliminado.")
                .data(supplierResponse)
                .build());
    }

}
