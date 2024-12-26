package com.impact.core.module.assetStatus.repository;

import com.impact.core.module.assetStatus.entity.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetStatusRepository extends JpaRepository<AssetStatus, Integer> {
}
