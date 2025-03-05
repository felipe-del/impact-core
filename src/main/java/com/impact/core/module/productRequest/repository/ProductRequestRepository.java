package com.impact.core.module.productRequest.repository;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.productRequest.entity.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer> {
    @Query("SELECT p FROM ProductRequest p WHERE p.user.id = :userId")
    List<ProductRequest> productsRequestByUser(@Param("userId") Integer userId);

    @Modifying
    @Query("UPDATE ProductRequest p SET p.status.id = :statusId WHERE p.id= :productId")
    void updateProductRequestStatus(@Param("statusId") Integer statusId, @Param("productId") Integer productId);
}
