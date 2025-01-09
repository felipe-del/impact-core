package com.impact.core.module.assetRequest.repository;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {

}
