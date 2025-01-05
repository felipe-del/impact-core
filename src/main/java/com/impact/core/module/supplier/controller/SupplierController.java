package com.impact.core.module.supplier.controller;

import com.impact.core.module.supplier.payload.request.SupplierRequest;
import com.impact.core.module.supplier.payload.response.SupplierResponse;
import com.impact.core.module.supplier.service.SupplierService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    public final SupplierService supplierService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<SupplierResponse>>> getAllSuppliers() {
        List<SupplierResponse> supplierResponses = supplierService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<SupplierResponse>>builder()
                .message("Lista de proveedores.")
                .data(supplierResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SupplierResponse>> saveSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.save(supplierRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<SupplierResponse>builder()
                .message("Proveedor guardado.")
                .data(supplierResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SupplierResponse>> updateSupplier(@PathVariable Integer id, @Valid @RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.update(id, supplierRequest);

        return ResponseEntity.ok(ResponseWrapper.<SupplierResponse>builder()
                .message("Proveedor actualizado.")
                .data(supplierResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SupplierResponse>> deleteSupplier(@PathVariable Integer id) {
        SupplierResponse supplierResponse = supplierService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<SupplierResponse>builder()
                .message("Proveedor eliminado.")
                .data(supplierResponse)
                .build());
    }

}
