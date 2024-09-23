package com.impact.brain.supplier.controller;

import com.impact.brain.supplier.dto.SupplierDTO;
import com.impact.brain.supplier.entity.EntityType;
import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.service.impl.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:52 AM
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<SupplierDTO> getAllSuppliers() {
        return supplierService.getSuppliers();
    }

    @GetMapping("/allEntityType")
    public ResponseEntity<Iterable<EntityType>> getAllEntityType() {
        try {
            Iterable<EntityType> entityTypes = supplierService.getEntityType();
            return ResponseEntity.ok(entityTypes); // 200 OK
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @PostMapping
    public ResponseEntity<Supplier> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier savedSupplier = supplierService.save(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSupplier);
    }

}
