package com.impact.core.module.asset.repository;

import com.impact.core.module.asset.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for {@link Asset} entity.
 * Provides custom queries for asset status updates, inventory valuation,
 * and filtering by purchase date.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {

    /**
     * Retrieves the sum of asset values and count per currency
     * for assets purchased within a specified date range.
     *
     * @param start_date the start date of the range
     * @param end_date   the end date of the range
     * @return a list of Object arrays, each containing:
     *         [0] - currency,
     *         [1] - total value (as BigDecimal),
     *         [2] - count (as Long)
     */
    @Query("SELECT a.currency, SUM(a.value), COUNT(*) FROM Asset a WHERE a.purchaseDate BETWEEN :start_date AND :end_date GROUP BY a.currency")
    List<Object[]> inventoryValueInAPeriod(@Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    /**
     * Updates the status of an asset based on its plate number.
     *
     * @param statusId the ID of the new status
     * @param assetId  the plate number (ID) of the asset
     */
    @Modifying
    @Query("UPDATE Asset a SET a.status.id= :statusId WHERE a.plateNumber= :assetId")
    void updateAssetStatus(@Param("statusId") Integer statusId, @Param("assetId") String assetId);

    /**
     * Finds all assets purchased between the given start and end dates.
     *
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return list of assets purchased in the date range
     */
    @Query("SELECT a FROM Asset a WHERE a.purchaseDate BETWEEN :startDate AND :endDate")
    List<Asset> findAllByPurchaseDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
