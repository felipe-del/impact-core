package com.impact.core.module.productCategory.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.productCategory.entity.ProductCategory;
import com.impact.core.module.productCategory.mapper.ProductCategoryMapper;
import com.impact.core.module.productCategory.payload.request.ProductCategoryRequest;
import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import com.impact.core.module.productCategory.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing {@link ProductCategory} entities.
 * Provides methods to perform CRUD operations and map entities to corresponding {@link ProductCategoryResponse} DTOs.
 */
@Service("productCategoryService")
@RequiredArgsConstructor
public class ProductCategoryService {
    public final ProductCategoryRepository productCategoryRepository;
    public final ProductCategoryMapper productCategoryMapper;

    /**
     * Saves a new {@link ProductCategory} entity in the repository.
     *
     * @param productCategoryRequest the request data to create a new product category.
     * @return the saved {@link ProductCategoryResponse} DTO.
     */
    public ProductCategoryResponse save(ProductCategoryRequest productCategoryRequest) {
        ProductCategory productCategory = productCategoryMapper.toEntity(productCategoryRequest);
        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);
        return productCategoryMapper.toDTO(savedProductCategory);
    }

    /**
     * Updates an existing {@link ProductCategory} entity by its ID.
     *
     * @param id the ID of the product category to update.
     * @param productCategoryRequest the request data to update the product category.
     * @return the updated {@link ProductCategoryResponse} DTO.
     * @throws ResourceNotFoundException if no {@link ProductCategory} is found with the specified ID.
     */
    public ProductCategoryResponse update(int id, ProductCategoryRequest productCategoryRequest) {
        ProductCategory productCategory = findById(id);
        ProductCategory updatedProductCategory = productCategoryMapper.toEntity(productCategoryRequest);
        updatedProductCategory.setId(productCategory.getId());
        ProductCategory savedProductCategory = productCategoryRepository.save(updatedProductCategory);
        return productCategoryMapper.toDTO(savedProductCategory);
    }

    /**
     * Deletes a {@link ProductCategory} entity by its ID.
     *
     * @param id the ID of the product category to delete.
     * @return the {@link ProductCategoryResponse} DTO of the deleted product category.
     * @throws ResourceNotFoundException if no {@link ProductCategory} is found with the specified ID.
     */
    public ProductCategoryResponse delete(int id) {
        ProductCategory productCategory = findById(id);
        productCategoryRepository.delete(productCategory);
        return productCategoryMapper.toDTO(productCategory);
    }

    /**
     * Retrieves a {@link ProductCategory} by its ID.
     *
     * @param id the ID of the product category to retrieve.
     * @return the {@link ProductCategory} entity.
     * @throws ResourceNotFoundException if no {@link ProductCategory} is found with the specified ID.
     */
    public ProductCategory findById(int id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría de producto con el id: " + id + " no existe."));
    }

    /**
     * Retrieves all {@link ProductCategory} entities from the repository and converts them into DTOs.
     *
     * @return a list of {@link ProductCategoryResponse} DTOs representing all product categories.
     */
    public List<ProductCategoryResponse> findAll() {
        return productCategoryRepository.findAll().stream()
                .map(productCategoryMapper::toDTO)
                .toList();
    }
}
