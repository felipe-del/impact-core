package com.impact.brain.asset.service;

import com.impact.brain.asset.dto.AssetDTO;
import com.impact.brain.asset.entity.Asset;
import com.impact.brain.asset.entity.AssetCategory;
import com.impact.brain.asset.entity.AssetStatus;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:22 AM
 */
public interface IAssetService {
    Iterable<Asset> all();
    Iterable<AssetStatus> allStatus();
    Iterable<AssetCategory> allCategories();
    Asset findById(int id);
    Asset mapper_DTOtoEntity(AssetDTO dto);
    AssetCategory findCategoryById(int id);
    Asset save(AssetDTO dto);
}
