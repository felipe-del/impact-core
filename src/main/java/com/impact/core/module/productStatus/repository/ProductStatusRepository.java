package com.impact.core.module.productStatus.repository;

import com.impact.core.module.productStatus.entity.ProductStatus;
import com.impact.core.module.productStatus.enun.EProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for interacting with the ProductStatus entity.
 * <p>
 * This repository provides the basic CRUD operations for managing product statuses
 * in the database, including the ability to find a product status by its name.
 * </p>
 */
@Repository
public interface ProductStatusRepository extends JpaRepository<ProductStatus, Integer> {

    /**
     * Finds a product status by its name.
     * <p>
     * This method allows searching for a product status by its enum name,
     * such as "PRODUCT_STATUS_AVAILABLE" or "PRODUCT_STATUS_LOANED".
     * </p>
     *
     * @param name the name of the product status to find.
     * @return an Optional containing the found product status, or empty if not found.
     */
    Optional<ProductStatus> findByName(EProductStatus name);
}
