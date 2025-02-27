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

@Service("assetSubCategoryService")
@RequiredArgsConstructor
public class AssetSubcategoryService {
    public final AssetSubcategoryRepository assetSubcategoryRepository;
    public final AssetSubcategoryMapper assetSubcategoryMapper;

    public AssetSubcategoryResponse save(AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetSubcategory assetSubcategory = assetSubcategoryMapper.toEntity(assetSubcategoryRequest);
        AssetSubcategory savedAssetSubcategory = assetSubcategoryRepository.save(assetSubcategory);
        return assetSubcategoryMapper.toDTO(savedAssetSubcategory);
    }

    public AssetSubcategoryResponse update(int id, AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetSubcategory assetSubcategory = this.findById(id);
        AssetSubcategory updatedAssetSubcategory = assetSubcategoryMapper.toEntity(assetSubcategoryRequest);
        updatedAssetSubcategory.setId(assetSubcategory.getId());
        AssetSubcategory savedAssetSubcategory = assetSubcategoryRepository.save(updatedAssetSubcategory);
        return assetSubcategoryMapper.toDTO(savedAssetSubcategory);
    }

    public AssetSubcategoryResponse delete(int id) {
        AssetSubcategory assetSubcategory = this.findById(id);
        assetSubcategoryRepository.delete(assetSubcategory);
        return assetSubcategoryMapper.toDTO(assetSubcategory);
    }

    public AssetSubcategory findById(int id) {
        return assetSubcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la subcategoría de activo con el id: " + id));
    }

    public List<AssetSubcategoryResponse> findAll() {
        return assetSubcategoryRepository.findAll().stream()
                .map(assetSubcategoryMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}
