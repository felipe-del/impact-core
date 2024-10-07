package com.impact.brain.asset.assetRequest.entity;

import com.impact.brain.asset.entity.Asset;
import com.impact.brain.request.entity.Request;
import com.impact.brain.request.entity.ResourceRequestStatus;
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
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ResourceRequestStatus status;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Lob
    @Column(name = "reason")
    private String reason;

}