package com.impact.brain.supplier.controller;

import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.service.impl.SupplierService;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<Supplier> getAllSuppliers() {
        return supplierService.getSuppliers();
    }

    @PostMapping
    public Supplier saveSupplier(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }
}
