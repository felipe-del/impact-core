package com.impact.core.module.productRequest.repository;

import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link ProductRequest} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for the {@link ProductRequest} entity.
 * Additionally, it defines custom query methods for retrieving {@link ProductRequest} objects based on user ID,
 * status ID, and for updating the status of a specific product request.
 * </p>
 */
@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer> {

    /**
     * Retrieves a list of {@link ProductRequest} entities associated with a specific {@link User} by their user ID.
     *
     * @param userId the ID of the {@link User} whose product requests are to be retrieved.
     * @return a list of {@link ProductRequest} entities associated with the given user.
     */
    @Query("SELECT p FROM ProductRequest p WHERE p.user.id = :userId")
    List<ProductRequest> productsRequestByUser(@Param("userId") Integer userId);


    /**
     * Retrieves a list of {@link ProductRequest} entities filtered by a specific status ID.
     *
     * @param status the ID of the {@link ResourceRequestStatus} to filter the product requests by.
     * @return a list of {@link ProductRequest} entities with the specified status.
     */
    @Query("SELECT p FROM ProductRequest p WHERE p.status.id = :status")
    List<ProductRequest> productsRequestByStatus(@Param("status") Integer status);

    /**
     * Updates the status of a {@link ProductRequest} entity by its product ID.
     * This query modifies the status of the product request identified by the given product ID.
     *
     * @param statusId the new status ID to assign to the {@link ProductRequest}.
     * @param productId the ID of the {@link ProductRequest} to update.
     */
    @Modifying
    @Query("UPDATE ProductRequest p SET p.status.id = :statusId WHERE p.id= :productId")
    void updateProductRequestStatus(@Param("statusId") Integer statusId, @Param("productId") Integer productId);
}
