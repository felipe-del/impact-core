package com.impact.core.module.brand.repository;

import com.impact.core.module.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the `Brand` entity.
 * This interface provides methods to perform CRUD operations on the `brand` table in the database.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
