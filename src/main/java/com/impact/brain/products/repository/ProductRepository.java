package com.impact.brain.products.repository;

import com.impact.brain.products.entity.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId")
    Long countProductsByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.status.id = 1")
    List<Product> findAvailableProductsByCategory(@Param("categoryName") String categoryName);

}
