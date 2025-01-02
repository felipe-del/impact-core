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

@Service("productCategoryService")
@RequiredArgsConstructor
public class ProductCategoryService {
    public final ProductCategoryRepository productCategoryRepository;
    public final ProductCategoryMapper productCategoryMapper;

    public ProductCategoryResponse save(ProductCategoryRequest productCategoryRequest) {
        ProductCategory productCategory = productCategoryMapper.toEntity(productCategoryRequest);
        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);
        return productCategoryMapper.toDTO(savedProductCategory);
    }

    public ProductCategoryResponse update(int id, ProductCategoryRequest productCategoryRequest) {
        ProductCategory productCategory = findById(id);
        ProductCategory updatedProductCategory = productCategoryMapper.toEntity(productCategoryRequest);
        updatedProductCategory.setId(productCategory.getId());
        ProductCategory savedProductCategory = productCategoryRepository.save(updatedProductCategory);
        return productCategoryMapper.toDTO(savedProductCategory);
    }

    public ProductCategoryResponse delete(int id) {
        ProductCategory productCategory = findById(id);
        productCategoryRepository.delete(productCategory);
        return productCategoryMapper.toDTO(productCategory);
    }

    public ProductCategory findById(int id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría de producto con el id: " + id + " no existe."));
    }

    public List<ProductCategoryResponse> findAll() {
        return productCategoryRepository.findAll().stream()
                .map(productCategoryMapper::toDTO)
                .toList();
    }
}
