package com.impact.core.module.assetStatus.repository;

import com.impact.core.module.assetStatus.entity.AssetStatus;
import com.impact.core.module.assetStatus.enun.EAssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link AssetStatus} entities.
 * This interface provides methods to perform CRUD operations on the asset status data.
 */
@Repository
public interface AssetStatusRepository extends JpaRepository<AssetStatus, Integer> {

    /**
     * Finds an {@link AssetStatus} entity by its name.
     *
     * @param name The {@link EAssetStatus} representing the name of the asset status.
     * @return An {@link Optional} containing the found {@link AssetStatus}, or empty if not found.
     */
    Optional<AssetStatus> findByName(EAssetStatus name);
}
