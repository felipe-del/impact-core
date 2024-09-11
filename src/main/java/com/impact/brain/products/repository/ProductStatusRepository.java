package com.impact.brain.products.repository;

import com.impact.brain.products.entity.ProductStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductStatusRepository extends CrudRepository<ProductStatus, Integer> {
    @Query("select s from ProductStatus s where s.name= ?1")
    ProductStatus findByName(String name);
}
