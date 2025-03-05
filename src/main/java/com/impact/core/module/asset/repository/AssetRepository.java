package com.impact.core.module.asset.repository;

import com.impact.core.module.asset.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {

    @Query("SELECT a.currency, SUM(a.value), COUNT(*) FROM Asset a WHERE a.purchaseDate BETWEEN :start_date AND :end_date GROUP BY a.currency")
    List<Object[]> inventoryValueInAPeriod(@Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Modifying
    @Query("UPDATE Asset a SET a.status.id= :statusId WHERE a.plateNumber= :assetId")
    void updateAssetStatus(@Param("statusId") Integer statusId, @Param("assetId") String assetId);
}
