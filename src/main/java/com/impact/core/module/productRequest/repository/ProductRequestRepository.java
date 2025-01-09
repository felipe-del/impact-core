package com.impact.core.module.productRequest.repository;

import com.impact.core.module.productRequest.entity.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer> {
}
