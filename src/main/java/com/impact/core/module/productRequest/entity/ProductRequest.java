package com.impact.core.module.productRequest.entity;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a request for a {@link Product} made by a {@link User}.
 * <p>
 * This entity maps to the "product_request" table in the database and holds details such as the product,
 * request status, reason for the request, and the user who made the request. The entity also manages the
 * timestamp of when the request was created.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_request")
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    /**
     * Unique identifier for the {@link ProductRequest} entity.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The {@link Product} associated with this {@link ProductRequest}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    /**
     * The {@link ResourceRequestStatus} that represents the current status of this request.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ResourceRequestStatus status;

    /**
     * The reason provided for making this {@link ProductRequest}.
     */
    @Lob
    @Column(name = "reason")
    private String reason;


    /**
     * The {@link User} who made the request for the {@link Product}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The timestamp indicating when the {@link ProductRequest} was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Pre-persist lifecycle method to set the creation timestamp if it is not already set.
     * This method ensures that the {@code createdAt} field is initialized with the current time.
     */
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}