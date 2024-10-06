package com.impact.brain.products.repository;

import com.impact.brain.products.entity.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Isaac F. B. C.
 * @since 10/6/2024 - 11:21 AM
 */
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer> {
}
