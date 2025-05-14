package com.impact.core.module.productCategory.repository;

import com.impact.core.module.productCategory.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link ProductCategory} entities.
 * Extends {@link JpaRepository} to provide CRUD operations on {@link ProductCategory} entities.
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
