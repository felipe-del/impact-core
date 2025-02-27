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

@Service("categoryTypeService")
@RequiredArgsConstructor
public class CategoryTypeService {
    public final CategoryTypeRepository categoryTypeRepository;
    public final CategoryTypeMapper categoryTypeMapper;

    public CategoryTypeResponse save(CategoryTypeRequest categoryTypeRequest) {
        CategoryType categoryType = categoryTypeMapper.toEntity(categoryTypeRequest);
        CategoryType savedCategoryType = categoryTypeRepository.save(categoryType);
        return categoryTypeMapper.toDTO(savedCategoryType);
    }

    public CategoryTypeResponse update(int id, CategoryTypeRequest categoryTypeRequest) {
        CategoryType categoryType = findById(id);
        CategoryType updatedCategoryType = categoryTypeMapper.toEntity(categoryTypeRequest);
        updatedCategoryType.setId(categoryType.getId());
        CategoryType savedCategoryType = categoryTypeRepository.save(updatedCategoryType);
        return categoryTypeMapper.toDTO(savedCategoryType);
    }

    public CategoryTypeResponse delete(int id) {
        CategoryType categoryType = findById(id);
        categoryTypeRepository.delete(categoryType);
        return categoryTypeMapper.toDTO(categoryType);
    }

    public CategoryType findById(int id) {
        return categoryTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de categoría con id: " + id + " no encontrado."));
    }

    public List<CategoryTypeResponse> findAll() {
        return categoryTypeRepository.findAll().stream()
                .map(categoryTypeMapper::toDTO)
                .toList();
    }

}
