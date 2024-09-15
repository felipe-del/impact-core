package com.impact.brain.brand.controller;

import com.impact.brain.asset.entity.Asset;
import com.impact.brain.brand.entity.Brand;
import com.impact.brain.brand.service.impl.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 7:03 AM
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    public final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public Iterable<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }
    @PostMapping
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.saveBrand(brand);
    }
}
