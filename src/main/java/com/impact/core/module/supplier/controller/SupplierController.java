package com.impact.core.module.supplier.controller;

import com.impact.core.module.supplier.entity.Supplier;
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

/**
 * Controller class for managing {@link Supplier} entities.
 * <p>
 * This controller handles requests related to the {@link Supplier}, such as retrieving, creating, updating, and deleting supplier records.
 */
@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    public final SupplierService supplierService;

    /**
     * Retrieves all {@link SupplierResponse} DTOs.
     * <p>
     * This method returns a list of all {@link SupplierResponse} DTOs. Only users with the roles 'ADMINISTRATOR' or 'MANAGER'
     * are authorized to access this endpoint.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link SupplierResponse} DTOs.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<SupplierResponse>>> getAllSuppliers() {
        List<SupplierResponse> supplierResponses = supplierService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<SupplierResponse>>builder()
                .message("Lista de proveedores.")
                .data(supplierResponses)
                .build());
    }

    /**
     * Creates a new {@link Supplier}.
     * <p>
     * This method creates a new supplier using the information provided in the {@link SupplierRequest} body. Only users
     * with the roles 'ADMINISTRATOR' or 'MANAGER' are authorized to access this endpoint.
     *
     * @param supplierRequest The {@link SupplierRequest} containing the information to create a new supplier.
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the created {@link SupplierResponse} DTO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SupplierResponse>> saveSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.save(supplierRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<SupplierResponse>builder()
                .message("Proveedor guardado.")
                .data(supplierResponse)
                .build());
    }

    /**
     * Updates an existing {@link Supplier}.
     * <p>
     * This method updates the supplier with the specified {@code id} using the information provided in the {@link SupplierRequest} body.
     * Only users with the roles 'ADMINISTRATOR' or 'MANAGER' are authorized to access this endpoint.
     *
     * @param id              The ID of the supplier to be updated.
     * @param supplierRequest The {@link SupplierRequest} containing the updated information for the supplier.
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the updated {@link SupplierResponse} DTO.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<SupplierResponse>> updateSupplier(@PathVariable Integer id, @Valid @RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.update(id, supplierRequest);

        return ResponseEntity.ok(ResponseWrapper.<SupplierResponse>builder()
                .message("Proveedor actualizado.")
                .data(supplierResponse)
                .build());
    }

    /**
     * Deletes an existing {@link Supplier}.
     * <p>
     * This method deletes the supplier with the specified {@code id}. Only users with the roles 'ADMINISTRATOR' or 'MANAGER'
     * are authorized to access this endpoint.
     *
     * @param id The ID of the supplier to be deleted.
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the deleted {@link SupplierResponse} DTO.
     */
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
