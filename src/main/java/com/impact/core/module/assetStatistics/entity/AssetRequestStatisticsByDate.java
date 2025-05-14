package com.impact.core.module.assetStatistics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

/**
 * Entity representing the mapping for the database view 'asset_request_statistics_by_date'.
 * This entity contains statistics related to asset requests, including their status,
 * request date, and the number of assets requested.
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name="asset_request_statistics_by_date")
public class AssetRequestStatisticsByDate {

    /**
     * Unique identifier for the asset request.
     */
    @NotNull
    @Id
    @Column(name = "asset_request_id", nullable = false)
    private Integer assetRequestId;


    /**
     * The identifier for the status of the asset.
     */
    @Column(name = "status_id")
    private Integer assetStatusId;

    /**
     * The identifier for the request status.
     */
    @Column(name = "request_status_id")
    private Integer requestStatusId;

    /**
     * The total number of assets requested for a given request.
     */
    @Column(name = "total_assets_requested")
    private Long totalAssetsRequested;
    /**
     * The date the asset request was made.
     */
    @Column(name = "request_date")
    private LocalDate requestDate;

}
