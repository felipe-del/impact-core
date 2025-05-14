package com.impact.core.module.product.repository;

import com.impact.core.module.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Product} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide
 * CRUD operations for {@link Product} entities, along with custom queries
 * for retrieving and modifying product data in the database.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Retrieves a list of available products by category name.
     * <p>
     * This query selects products from the {@link Product} entity where
     * the category name matches the provided {@code categoryName} and the product's status is set to available (status ID 1).
     *
     * @param categoryName the name of the product category
     * @return a list of available products in the specified category
     */
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.status.id = 1")
    List<Product> findAvailableProductsByCategory(@Param("categoryName") String categoryName);

    /**
     * Updates the status of a product based on its ID.
     * <p>
     * This query modifies the status of a product by setting the product's status ID to the provided {@code statusId}.
     *
     * @param statusId the new status ID to set for the product
     * @param productId the ID of the product whose status is being updated
     */
    @Modifying
    @Query("UPDATE Product p SET p.status.id= :statusId WHERE p.id= :productId")
    void updateProdductStatus(@Param("statusId") Integer statusId, @Param("productId") Integer productId);

    /**
     * Retrieves the count of products with a specific status.
     * <p>
     * This query counts the number of products in the {@link Product}
     * entity that have the specified {@code status_id}.
     *
     * @param status_id the status ID of the products to count
     * @return the number of products with the given status ID
     */
    @Query("SELECT COUNT(p) FROM Product p WHERE p.status.id = :status_id")
    Long remainingProducts(@Param("status_id")Integer status_id);
}
