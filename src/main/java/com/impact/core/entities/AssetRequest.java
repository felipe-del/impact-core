package com.impact.core.entities;

import com.impact.core.module.asset.entity.Asset;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "asset_request")
public class AssetRequest {
    @Id
    @Column(name = "id", nullable = false)
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

}