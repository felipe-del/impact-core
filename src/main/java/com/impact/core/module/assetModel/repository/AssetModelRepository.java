package com.impact.core.module.assetModel.repository;

import com.impact.core.module.assetModel.entity.AssetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link AssetModel} entities.
 * <p>
 * This interface provides the basic CRUD operations for {@link AssetModel} entities, such as saving, deleting, and retrieving asset models.
 * It extends {@link JpaRepository}, which offers additional functionality such as pagination and sorting.
 * </p>
 */
@Repository
public interface AssetModelRepository extends JpaRepository<AssetModel, Integer> {
}
