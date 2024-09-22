package com.impact.brain.asset.service;

import com.impact.brain.asset.dto.AssetDTO;
import com.impact.brain.asset.dto.AssetSubcategoryDTO;
import com.impact.brain.asset.entity.*;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:22 AM
 */
public interface IAssetService {
    Iterable<Asset> all();
    Iterable<AssetStatus> allStatus();
    Iterable<AssetCategory> allCategories();

    Iterable<Currency> allCurrency();

    Iterable<AssetSubcategoryDTO> allAssetSubcategory();

    Iterable<AssetModel> allAssetModel();

    Asset findById(int id);
    Asset mapper_DTOtoEntity(AssetDTO dto);

    AssetCategory mapper_DTOtoAssetCategory(AssetCategoryDTO dto);

    AssetCategory findCategoryById(int id);
    Asset save(AssetDTO dto);
    AssetCategory saveCategory(AssetCategory category);

    AssetSubcategory saveSubcategory(AssetSubcategoryDTO assetSubcategoryDTO);

    AssetModel saveModel(AssetModel assetModel);
}
