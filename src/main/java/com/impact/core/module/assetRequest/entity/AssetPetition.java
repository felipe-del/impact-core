package com.impact.core.module.assetRequest.entity;

import com.impact.core.module.asset.entity.Asset;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "asset_petition")
@NoArgsConstructor
@AllArgsConstructor
public class AssetPetition {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ResourceRequestStatus status;

    @Lob
    @Column(name = "reason")
    private String reason;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}