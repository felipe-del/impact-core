package com.impact.core.module.product.entity;

import com.impact.core.module.productCategory.entity.ProductCategory;
import com.impact.core.module.productStatus.entity.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entity representing a product record in the system.
 * <p>
 * This class is mapped to the {@code product} table in the database and includes
 * fields for managing key product details such as purchase and expiry dates,
 * along with its associated category and status.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Unique identifier for the product.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The date on which the product was purchased.
     */
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    /**
     * The expiration date of the product.
     */
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    /**
     * The category to which the product belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    /**
     * The current status of the product.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private ProductStatus status;

}