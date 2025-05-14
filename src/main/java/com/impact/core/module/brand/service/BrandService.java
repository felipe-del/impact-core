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

/**
 * Service class for managing the `Brand` entities.
 * This class provides methods to create, update, delete, and retrieve brands.
 */
@Service("brandService")
@RequiredArgsConstructor
public class BrandService {
    public final BrandRepository brandRepository;
    public final BrandMapper brandMapper;

    /**
     * Saves a new brand to the database.
     *
     * @param brandRequest the request object containing brand details.
     * @return the saved brand as a response object.
     */
    public BrandResponse save(BrandRequest brandRequest) {
        Brand brandMapped = brandMapper.toEntity(brandRequest);
        Brand savedBrand = brandRepository.save(brandMapped);
        return brandMapper.toDTO(savedBrand);
    }

    /**
     * Updates an existing brand in the database.
     *
     * @param id the id of the brand to update.
     * @param brandRequest the request object containing the new brand details.
     * @return the updated brand as a response object.
     * @throws ResourceNotFoundException if the brand with the specified id is not found.
     */
    public BrandResponse update(int id, BrandRequest brandRequest) {
        Brand brand = this.findById(id);
        brand.setName(brandRequest.getName());
        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.toDTO(updatedBrand);
    }

    /**
     * Deletes a brand from the database.
     *
     * @param id the id of the brand to delete.
     * @return the deleted brand as a response object.
     * @throws ResourceNotFoundException if the brand with the specified id is not found.
     */
    public BrandResponse delete(int id) {
        Brand brand = this.findById(id);
        brandRepository.delete(brand);
        return brandMapper.toDTO(brand);
    }

    /**
     * Finds a brand by its id.
     *
     * @param id the id of the brand to find.
     * @return the found brand entity.
     * @throws ResourceNotFoundException if the brand with the specified id is not found.
     */
    public Brand findById(int id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca con id " + id + " no encontrada."));
    }

    /**
     * Retrieves all brands from the database.
     *
     * @return a list of all brands as response objects.
     */
    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

}
