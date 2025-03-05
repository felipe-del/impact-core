package com.impact.core.module.product.repository;

import com.impact.core.module.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.status.id = 1")
    List<Product> findAvailableProductsByCategory(@Param("categoryName") String categoryName);
    @Modifying
    @Query("UPDATE Product p SET p.status.id= :statusId WHERE p.id= :productId")
    void updateProdductStatus(@Param("statusId") Integer statusId, @Param("productId") Integer productId);
}
