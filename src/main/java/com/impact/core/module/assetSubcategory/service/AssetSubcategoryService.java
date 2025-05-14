package com.impact.core.module.assetSubcategory.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import com.impact.core.module.assetSubcategory.mapper.AssetSubcategoryMapper;
import com.impact.core.module.assetSubcategory.payload.request.AssetSubcategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetSubcategoryResponse;
import com.impact.core.module.assetSubcategory.repository.AssetSubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing {@link AssetSubcategory} entities.
 * <p>
 * This service class contains business logic for performing CRUD operations on {@link AssetSubcategory} entities,
 * including saving, updating, deleting, and retrieving them. It uses {@link AssetSubcategoryRepository} for database
 * interactions and {@link AssetSubcategoryMapper} to convert between entities and DTOs.
 * </p>
 */
@Service("assetSubCategoryService")
@RequiredArgsConstructor
public class AssetSubcategoryService {
    public final AssetSubcategoryRepository assetSubcategoryRepository;
    public final AssetSubcategoryMapper assetSubcategoryMapper;

    /**
     * Saves a new {@link AssetSubcategory} entity.
     *
     * @param assetSubcategoryRequest the {@link AssetSubcategoryRequest} containing the data to be saved
     * @return a {@link AssetSubcategoryResponse} representing the saved {@link AssetSubcategory}
     */
    public AssetSubcategoryResponse save(AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetSubcategory assetSubcategory = assetSubcategoryMapper.toEntity(assetSubcategoryRequest);
        AssetSubcategory savedAssetSubcategory = assetSubcategoryRepository.save(assetSubcategory);
        return assetSubcategoryMapper.toDTO(savedAssetSubcategory);
    }

    /**
     * Updates an existing {@link AssetSubcategory} entity.
     *
     * @param id the ID of the {@link AssetSubcategory} to update
     * @param assetSubcategoryRequest the {@link AssetSubcategoryRequest} containing the updated data
     * @return a {@link AssetSubcategoryResponse} representing the updated {@link AssetSubcategory}
     */
    public AssetSubcategoryResponse update(int id, AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetSubcategory assetSubcategory = this.findById(id);
        AssetSubcategory updatedAssetSubcategory = assetSubcategoryMapper.toEntity(assetSubcategoryRequest);
        updatedAssetSubcategory.setId(assetSubcategory.getId());
        AssetSubcategory savedAssetSubcategory = assetSubcategoryRepository.save(updatedAssetSubcategory);
        return assetSubcategoryMapper.toDTO(savedAssetSubcategory);
    }

    /**
     * Deletes an existing {@link AssetSubcategory} entity.
     *
     * @param id the ID of the {@link AssetSubcategory} to delete
     * @return a {@link AssetSubcategoryResponse} representing the deleted {@link AssetSubcategory}
     */
    public AssetSubcategoryResponse delete(int id) {
        AssetSubcategory assetSubcategory = this.findById(id);
        assetSubcategoryRepository.delete(assetSubcategory);
        return assetSubcategoryMapper.toDTO(assetSubcategory);
    }

    /**
     * Finds an {@link AssetSubcategory} by its ID.
     *
     * @param id the ID of the {@link AssetSubcategory} to find
     * @return the found {@link AssetSubcategory}
     * @throws ResourceNotFoundException if no {@link AssetSubcategory} is found with the given ID
     */
    public AssetSubcategory findById(int id) {
        return assetSubcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la subcategoría de activo con el id: " + id));
    }

    /**
     * Finds all {@link AssetSubcategory} entities.
     *
     * @return a list of {@link AssetSubcategoryResponse} DTOs representing all {@link AssetSubcategory} entities
     */
    public List<AssetSubcategoryResponse> findAll() {
        return assetSubcategoryRepository.findAll().stream()
                .map(assetSubcategoryMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}
