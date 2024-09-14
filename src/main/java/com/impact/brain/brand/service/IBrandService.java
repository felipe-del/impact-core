package com.impact.brain.brand.service;

import com.impact.brain.brand.entity.Brand;

import java.util.Optional;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 7:03 AM
 */
public interface IBrandService {
    Optional<Brand> findById(int id);
    Brand saveBrand(Brand brand);
    Iterable<Brand> getAllBrands();
}
