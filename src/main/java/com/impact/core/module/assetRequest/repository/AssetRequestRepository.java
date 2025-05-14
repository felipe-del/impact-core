package com.impact.core.module.assetRequest.repository;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link AssetRequest} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic database operations, such as finding, saving,
 * and deleting asset request records. Custom queries are defined to filter requests by user and status.
 * </p>
 */
@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {

    /**
     * Finds all asset requests made by a specific user.
     * <p>
     * This method uses a custom query to filter the asset requests by the user ID.
     * </p>
     *
     * @param userId the ID of the user whose asset requests are to be retrieved.
     * @return a list of {@link AssetRequest} entities associated with the given user.
     */
    @Query("SELECT a FROM AssetRequest a WHERE a.user.id = :userId")
    List<AssetRequest> assetsRequestByUser(@Param("userId") Integer userId);

    /**
     * Finds all asset requests with a specific status.
     * <p>
     * This method uses a custom query to filter asset requests based on their status.
     * </p>
     *
     * @param status the ID of the status to filter asset requests by.
     * @return a list of {@link AssetRequest} entities with the given status.
     */
    @Query("SELECT a FROM AssetRequest a WHERE a.status.id = :status")
    List<AssetRequest> assetsRequestByStatus(@Param("status") Integer status);

    /**
     * Updates the status of an asset request.
     * <p>
     * This method allows for modifying the status of an existing asset request based on its ID.
     * It uses a {@link Modifying} query to perform the update operation.
     * </p>
     *
     * @param statusId the ID of the new status to set.
     * @param assetId the ID of the asset request whose status is to be updated.
     */
    @Modifying
    @Query("UPDATE AssetRequest a SET a.status.id = :statusId WHERE a.id= :assetId")
    void updateAssetRequestStatus(@Param("statusId") Integer statusId, @Param("assetId") Integer assetId);
}
