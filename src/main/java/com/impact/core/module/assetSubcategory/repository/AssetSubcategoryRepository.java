package com.impact.core.module.assetSubcategory.repository;

import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetSubcategoryRepository extends JpaRepository<AssetSubcategory, Integer> {
}
