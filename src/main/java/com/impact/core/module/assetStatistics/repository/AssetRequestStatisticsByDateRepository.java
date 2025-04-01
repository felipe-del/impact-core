package com.impact.core.module.assetStatistics.repository;

import com.impact.core.module.assetStatistics.entity.AssetRequestStatisticsByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRequestStatisticsByDateRepository extends JpaRepository<AssetRequestStatisticsByDate, Integer> {

    @Override
    List<AssetRequestStatisticsByDate> findAll();
}
