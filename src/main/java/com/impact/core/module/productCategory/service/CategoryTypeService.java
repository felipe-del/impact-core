package com.impact.core.module.productCategory.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.productCategory.entity.CategoryType;
import com.impact.core.module.productCategory.mapper.CategoryTypeMapper;
import com.impact.core.module.productCategory.payload.request.CategoryTypeRequest;
import com.impact.core.module.productCategory.payload.response.CategoryTypeResponse;
import com.impact.core.module.productCategory.repository.CategoryTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing {@link CategoryType} entities.
 * Provides methods to perform CRUD operations and map entities to corresponding {@link CategoryTypeResponse} DTOs.
 */
@Service("categoryTypeService")
@RequiredArgsConstructor
public class CategoryTypeService {
    public final CategoryTypeRepository categoryTypeRepository;
    public final CategoryTypeMapper categoryTypeMapper;

    /**
     * Saves a new {@link CategoryType} entity in the repository.
     *
     * @param categoryTypeRequest the request data to create a new category type.
     * @return the saved {@link CategoryTypeResponse} DTO.
     */
    public CategoryTypeResponse save(CategoryTypeRequest categoryTypeRequest) {
        CategoryType categoryType = categoryTypeMapper.toEntity(categoryTypeRequest);
        CategoryType savedCategoryType = categoryTypeRepository.save(categoryType);
        return categoryTypeMapper.toDTO(savedCategoryType);
    }

    /**
     * Updates an existing {@link CategoryType} entity by its ID.
     *
     * @param id the ID of the category type to update.
     * @param categoryTypeRequest the request data to update the category type.
     * @return the updated {@link CategoryTypeResponse} DTO.
     * @throws ResourceNotFoundException if no {@link CategoryType} is found with the specified ID.
     */
    public CategoryTypeResponse update(int id, CategoryTypeRequest categoryTypeRequest) {
        CategoryType categoryType = findById(id);
        CategoryType updatedCategoryType = categoryTypeMapper.toEntity(categoryTypeRequest);
        updatedCategoryType.setId(categoryType.getId());
        CategoryType savedCategoryType = categoryTypeRepository.save(updatedCategoryType);
        return categoryTypeMapper.toDTO(savedCategoryType);
    }

    /**
     * Deletes a {@link CategoryType} entity by its ID.
     *
     * @param id the ID of the category type to delete.
     * @return the {@link CategoryTypeResponse} DTO of the deleted category type.
     * @throws ResourceNotFoundException if no {@link CategoryType} is found with the specified ID.
     */
    public CategoryTypeResponse delete(int id) {
        CategoryType categoryType = findById(id);
        categoryTypeRepository.delete(categoryType);
        return categoryTypeMapper.toDTO(categoryType);
    }

    /**
     * Retrieves a {@link CategoryType} by its ID.
     *
     * @param id the ID of the category type to retrieve.
     * @return the {@link CategoryType} entity.
     * @throws ResourceNotFoundException if no {@link CategoryType} is found with the specified ID.
     */
    public CategoryType findById(int id) {
        return categoryTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de categoría con id: " + id + " no encontrado."));
    }

    /**
     * Retrieves all {@link CategoryType} entities from the repository and converts them into DTOs.
     *
     * @return a list of {@link CategoryTypeResponse} DTOs representing all category types.
     */
    public List<CategoryTypeResponse> findAll() {
        return categoryTypeRepository.findAll().stream()
                .map(categoryTypeMapper::toDTO)
                .toList();
    }

}
