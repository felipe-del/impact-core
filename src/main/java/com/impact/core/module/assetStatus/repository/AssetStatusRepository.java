package com.impact.core.module.assetStatus.repository;

import com.impact.core.module.assetStatus.entity.AssetStatus;
import com.impact.core.module.assetStatus.enun.EAssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetStatusRepository extends JpaRepository<AssetStatus, Integer> {
    Optional<AssetStatus> findByName(EAssetStatus name);
}
