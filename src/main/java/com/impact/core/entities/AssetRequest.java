package com.impact.core.entities;

import com.impact.core.module.asset.entity.Asset;
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
    @JoinColumn(name = "request_id")
    private com.impact.core.entities.Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private com.impact.core.entities.ResourceRequestStatus status;

    @Lob
    @Column(name = "reason")
    private String reason;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

}