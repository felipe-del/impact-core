package com.impact.brain.asset.service;

import ch.qos.logback.core.model.Model;
import com.impact.brain.asset.dto.AssetCategoryDTO;
import com.impact.brain.asset.dto.AssetDTO;
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

    Iterable<AssetSubcategory> allAssetSubcategory();

    Iterable<AssetModel> allAssetModel();

    Asset findById(int id);
    Asset mapper_DTOtoEntity(AssetDTO dto);

    AssetCategory mapper_DTOtoAssetCategory(AssetCategoryDTO dto);

    AssetCategory findCategoryById(int id);
    Asset save(AssetDTO dto);
    AssetCategory saveCategory(AssetCategory category);

    AssetSubcategory saveSubcategory(AssetSubcategory assetSubcategory);

    AssetModel saveModel(AssetModel assetModel);
}
