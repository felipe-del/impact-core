package com.impact.core.module.assetRequest.repository;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {

    @Query("SELECT a FROM AssetRequest a WHERE a.user.id = :userId")
    List<AssetRequest> assetsRequestByUser(@Param("userId") Integer userId);

    @Modifying
    @Query("UPDATE AssetRequest a SET a.status.id = :statusId WHERE a.id= :assetId")
    void updateAssetRequestStatus(@Param("statusId") Integer statusId, @Param("assetId") Integer assetId);
}
