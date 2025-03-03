package com.impact.core.module.assetRequest.repository;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {

    @Query("SELECT a FROM AssetRequest a WHERE a.asset.id = :userId")
    List<AssetRequest> assetsRequestByUser(@Param("userId") Integer userId);

}
