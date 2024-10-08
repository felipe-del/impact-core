package com.impact.brain.asset.assetRequest.service;

import com.impact.brain.asset.assetRequest.dto.AssetRequestDTO;
import com.impact.brain.asset.assetRequest.entity.AssetRequest;

/**
 * @author Isaac F. B. C.
 * @since 10/1/2024 - 7:32 PM
 */
public interface IAssetRequestService {
    Iterable<AssetRequest> findAll();
    AssetRequestDTO save(AssetRequestDTO assetRequestDTO);
}
