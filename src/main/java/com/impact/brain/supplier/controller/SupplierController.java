package com.impact.brain.supplier.controller;

import com.impact.brain.supplier.dto.SupplierDTO;
import com.impact.brain.supplier.entity.EntityType;
import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.service.impl.SupplierService;
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
    public Iterable<EntityType> getAllEntityType() {
        return supplierService.getEntityType();
    }

    @PostMapping
    public Supplier saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        return supplierService.save(supplierDTO);
    }
}
