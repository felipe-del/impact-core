package com.impact.core.module.assetRequest.entity;

import com.impact.core.module.asset.entity.Asset;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing an asset request.
 * <p>
 * This entity stores information about requests made for assets, including the asset being requested,
 * the status of the request, the user who made the request, and additional details such as the reason and expiration date.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "asset_request")
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequest {

    /**
     * Unique identifier for the asset request.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The asset being requested.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    /**
     * The current status of the asset request.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ResourceRequestStatus status;

    /**
     * The reason for making the asset request.
     */
    @Lob
    @Column(name = "reason")
    private String reason;

    /**
     * The expiration date for the asset request.
     */
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    /**
     * The user who made the asset request.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The timestamp when the asset request was created.
     * This is automatically set to the current date and time when the entity is persisted for the first time.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Method to automatically set the createdAt timestamp before the entity is persisted.
     * This ensures that the createdAt field is populated if it was not set before saving.
     */
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}