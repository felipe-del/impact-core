package com.impact.core.module.productsOfRequest.repository;

import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productsOfRequest.entity.ProductsOfRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for accessing {@link ProductsOfRequest} entities from the database.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods to query the
 * "products_of_request" table, including custom queries for retrieving data based on specific conditions.
 * </p>
 */
public interface ProductsOfRequestRepository extends JpaRepository<com.impact.core.module.productsOfRequest.entity.ProductsOfRequest, Integer> {

    /**
     * Retrieves a list of {@link ProductsOfRequest} entities associated with a specific {@link ProductRequest}.
     * <p>
     * This method uses a custom query to find all {@link ProductsOfRequest} records where the
     * {@link ProductRequest} id matches the provided {@code requestId}.
     * </p>
     *
     * @param requestId the id of the {@link ProductRequest} to filter by.
     * @return a list of {@link ProductsOfRequest} matching the given {@code requestId}.
     */
    @Query("SELECT p FROM ProductsOfRequest p WHERE p.productRequest.id = :requestId")
    List<ProductsOfRequest> productsOfRequestByR(@Param("requestId") Integer requestId);
}
