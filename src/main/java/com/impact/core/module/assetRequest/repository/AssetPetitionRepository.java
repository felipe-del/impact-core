package com.impact.core.module.assetRequest.repository;

import com.impact.core.module.assetRequest.entity.AssetPetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetPetitionRepository extends JpaRepository<AssetPetition, Integer> {

}
