package com.impact.core.module.productsOfRequest.entity;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.productRequest.entity.ProductRequest;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents the entity for the relationship between {@link Product} and {@link ProductRequest}.
 * <p>
 * This entity maps to the "products_of_request" table in the database.
 * It contains information regarding the specific {@link Product} associated with a {@link ProductRequest}.
 * </p>
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "products_of_request")
@NoArgsConstructor
@AllArgsConstructor
public class ProductsOfRequest {

    /**
     * Unique identifier for the {@link ProductsOfRequest} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * The {@link Product} associated with this {@link ProductsOfRequest} entity.
     * The fetch type is set to lazy loading to optimize performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * The {@link ProductRequest} associated with this {@link ProductsOfRequest} entity.
     * The fetch type is set to lazy loading to optimize performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_request_id")
    private ProductRequest productRequest;

}
