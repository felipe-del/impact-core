package com.impact.core.module.assetSubcategory.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import com.impact.core.module.assetSubcategory.mapper.AssetCategoryMapper;
import com.impact.core.module.assetSubcategory.payload.request.AssetCategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetCategoryResponse;
import com.impact.core.module.assetSubcategory.repository.AssetCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing {@link AssetCategory} entities.
 * <p>
 * This service class contains business logic for performing CRUD operations on {@link AssetCategory} entities,
 * including saving, updating, deleting, and retrieving them. It uses {@link AssetCategoryRepository} for database
 * interactions and {@link AssetCategoryMapper} to convert between entities and DTOs.
 * </p>
 */
@Service("assetCategoryService")
@RequiredArgsConstructor
public class AssetCategoryService {
    public final AssetCategoryRepository assetCategoryRepository;
    public final AssetCategoryMapper assetCategoryMapper;

    /**
     * Saves a new {@link AssetCategory} entity.
     *
     * @param assetCategoryRequest the {@link AssetCategoryRequest} containing the data to be saved
     * @return a {@link AssetCategoryResponse} representing the saved {@link AssetCategory}
     */
    public AssetCategoryResponse save(AssetCategoryRequest assetCategoryRequest) {
        AssetCategory assetCategory = assetCategoryMapper.toEntity(assetCategoryRequest);
        AssetCategory savedAssetCategory = assetCategoryRepository.save(assetCategory);
        return assetCategoryMapper.toDTO(savedAssetCategory);
    }

    /**
     * Updates an existing {@link AssetCategory} entity.
     *
     * @param id the ID of the {@link AssetCategory} to update
     * @param assetCategoryRequest the {@link AssetCategoryRequest} containing the updated data
     * @return a {@link AssetCategoryResponse} representing the updated {@link AssetCategory}
     */
    public AssetCategoryResponse update(int id, AssetCategoryRequest assetCategoryRequest) {
        AssetCategory assetCategory = this.findById(id);
        assetCategory.setName(assetCategoryRequest.getName());
        AssetCategory updatedAssetCategory = assetCategoryRepository.save(assetCategory);
        return assetCategoryMapper.toDTO(updatedAssetCategory);
    }

    /**
     * Deletes an existing {@link AssetCategory} entity.
     *
     * @param id the ID of the {@link AssetCategory} to delete
     * @return a {@link AssetCategoryResponse} representing the deleted {@link AssetCategory}
     */
    public AssetCategoryResponse delete(int id) {
        AssetCategory assetCategory = this.findById(id);
        assetCategoryRepository.delete(assetCategory);
        return assetCategoryMapper.toDTO(assetCategory);
    }

    /**
     * Finds a {@link AssetCategory} by its ID.
     *
     * @param id the ID of the {@link AssetCategory} to find
     * @return the found {@link AssetCategory}
     * @throws ResourceNotFoundException if no {@link AssetCategory} is found with the given ID
     */
    public AssetCategory findById(int id) {
        return assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la categoría de activo con el id: " + id));
    }

    /**
     * Finds all {@link AssetCategory} entities.
     *
     * @return a list of {@link AssetCategoryResponse} DTOs representing all {@link AssetCategory} entities
     */
    public List<AssetCategoryResponse> findAll() {
        return assetCategoryRepository.findAll().stream()
                .map(assetCategoryMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}
