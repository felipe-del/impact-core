package com.impact.core.module.productStatus.entity;

import com.impact.core.module.productStatus.enun.EProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entity representing a product status in the system.
 * <p>
 * The product status indicates the current state of a product, such as "Available", "Out of Stock", etc.
 * This class is used to persist product status data in the database.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_status")
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatus {

    /**
     * Unique identifier for the product status.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Name of the product status. This field maps to the {@link EProductStatus} enumeration.
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EProductStatus name;

    /**
     * A description providing additional details about the product status.
     */
    @Lob
    @Column(name = "description")
    private String description;

}