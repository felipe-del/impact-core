package com.impact.core.module.assetStatistics.repository;

import com.impact.core.module.assetStatistics.entity.AssetRequestStatisticsByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing the 'asset_request_statistics_by_date' database view.
 * Provides methods to perform CRUD operations on the {@link AssetRequestStatisticsByDate} entity.
 */
@Repository
public interface AssetRequestStatisticsByDateRepository extends JpaRepository<AssetRequestStatisticsByDate, Integer> {

    /**
     * Retrieves all the asset request statistics from the database.
     * This method is inherited from {@link JpaRepository}, and it returns all records from the 'asset_request_statistics_by_date' view.
     *
     * @return A list of {@link AssetRequestStatisticsByDate} entities containing asset request statistics.
     */
    @Override
    List<AssetRequestStatisticsByDate> findAll();
}
