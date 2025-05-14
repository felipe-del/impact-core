package com.impact.core.module.assetSubcategory.repository;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link AssetCategory} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom query methods
 * for {@link AssetCategory} entities. It includes a method to retrieve all {@link AssetCategory} entities.
 * </p>
 */
@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Integer> {

    /**
     * Retrieves all {@link AssetCategory} entities from the database.
     *
     * @return a list of all {@link AssetCategory} entities
     */
    List<AssetCategory> findAll();
}
