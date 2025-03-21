package com.impact.core.module.productsOfRequest.repository;


import com.impact.core.module.productsOfRequest.entity.ProductsOfRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsOfRequestRepository extends JpaRepository<com.impact.core.module.productsOfRequest.entity.ProductsOfRequest, Integer> {
    @Query("SELECT p FROM ProductsOfRequest p WHERE p.productRequest.id = :requestId")
    List<ProductsOfRequest> productsOfRequestByR(@Param("requestId") Integer requestId);
}
