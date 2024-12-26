package com.impact.core.module.assetSubcategory.repository;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Integer> {
    List<AssetCategory> findAll();

}
