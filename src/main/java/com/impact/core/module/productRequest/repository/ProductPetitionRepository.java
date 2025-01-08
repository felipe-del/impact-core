package com.impact.core.module.productRequest.repository;

import com.impact.core.module.productRequest.entity.ProductPetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPetitionRepository extends JpaRepository<ProductPetition, Integer> {
}
