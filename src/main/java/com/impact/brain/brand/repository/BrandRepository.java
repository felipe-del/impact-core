package com.impact.brain.brand.repository;

import com.impact.brain.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 7:03 AM
 */
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findByName(String name);
}
