package com.impact.brain.brand.service.impl;

import com.impact.brain.brand.entity.Brand;
import com.impact.brain.brand.repository.BrandRepository;
import com.impact.brain.brand.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 7:03 AM
 */
@Service
public class BrandService implements IBrandService {
    @Autowired
    final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Optional<Brand> findById(int id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Iterable<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
