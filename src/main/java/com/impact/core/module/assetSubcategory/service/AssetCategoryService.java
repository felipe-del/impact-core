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

@Service("assetCategoryService")
@RequiredArgsConstructor
public class AssetCategoryService {
    public final AssetCategoryRepository assetCategoryRepository;
    public final AssetCategoryMapper assetCategoryMapper;

    public AssetCategoryResponse save(AssetCategoryRequest assetCategoryRequest) {
        AssetCategory assetCategory = assetCategoryMapper.toEntity(assetCategoryRequest);
        AssetCategory savedAssetCategory = assetCategoryRepository.save(assetCategory);
        return assetCategoryMapper.toDTO(savedAssetCategory);
    }

    public AssetCategoryResponse update(int id, AssetCategoryRequest assetCategoryRequest) {
        AssetCategory assetCategory = this.findById(id);
        assetCategory.setName(assetCategoryRequest.getName());
        AssetCategory updatedAssetCategory = assetCategoryRepository.save(assetCategory);
        return assetCategoryMapper.toDTO(updatedAssetCategory);
    }

    public AssetCategoryResponse delete(int id) {
        AssetCategory assetCategory = this.findById(id);
        assetCategoryRepository.delete(assetCategory);
        return assetCategoryMapper.toDTO(assetCategory);
    }

    public AssetCategory findById(int id) {
        return assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la categoría de activo con el id: " + id));
    }

    public List<AssetCategoryResponse> findAll() {
        return assetCategoryRepository.findAll().stream()
                .map(assetCategoryMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }


}
