package com.impact.core.module.productCategory.repository;

import com.impact.core.module.productCategory.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link CategoryType} entities.
 * Extends {@link JpaRepository} to provide CRUD operations on {@link CategoryType} entities.
 */
@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {

}
