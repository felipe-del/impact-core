package com.impact.core.module.assetSubcategory.repository;

import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link AssetSubcategory} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for {@link AssetSubcategory} entities.
 * </p>
 */
@Repository
public interface AssetSubcategoryRepository extends JpaRepository<AssetSubcategory, Integer> {
}
