package com.impact.core.module.assetModel.repository;

import com.impact.core.module.assetModel.entity.AssetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetModelRepository extends JpaRepository<AssetModel, Integer> {
}
