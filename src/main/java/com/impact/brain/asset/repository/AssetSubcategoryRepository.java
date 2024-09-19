package com.impact.brain.asset.repository;

import com.impact.brain.asset.entity.AssetSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Isaac F. B. C.
 * @since 9/18/2024 - 10:10 PM
 */
public interface AssetSubcategoryRepository extends JpaRepository<AssetSubcategory, Integer> {
    AssetSubcategory findByName(String name);
}
