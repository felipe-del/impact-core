package com.impact.core.module.brand.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.brand.entity.Brand;
import com.impact.core.module.brand.mapper.BrandMapper;
import com.impact.core.module.brand.payload.request.BrandRequest;
import com.impact.core.module.brand.payload.response.BrandResponse;
import com.impact.core.module.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("brandService")
@RequiredArgsConstructor
public class BrandService {
    public final BrandRepository brandRepository;
    public final BrandMapper brandMapper;

    public BrandResponse save(BrandRequest brandRequest) {
        Brand brandMapped = brandMapper.toEntity(brandRequest);
        Brand savedBrand = brandRepository.save(brandMapped);
        return brandMapper.toDTO(savedBrand);
    }

    public BrandResponse update(int id, BrandRequest brandRequest) {
        Brand brand = this.findById(id);
        brand.setName(brandRequest.getName());
        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.toDTO(updatedBrand);
    }

    public BrandResponse delete(int id) {
        Brand brand = this.findById(id);
        brandRepository.delete(brand);
        return brandMapper.toDTO(brand);
    }

    public Brand findById(int id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca con id " + id + " no encontrada."));
    }

    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

}
