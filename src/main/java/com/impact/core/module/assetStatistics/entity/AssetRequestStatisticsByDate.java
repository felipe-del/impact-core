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
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name="asset_request_statistics_by_date")
public class AssetRequestStatisticsByDate {
    @NotNull
    @Id
    @Column(name = "asset_request_id", nullable = false)
    private Integer assetRequestId;

    @Column(name = "status_id")
    private Integer assetStatusId;

    @Column(name = "request_status_id")
    private Integer requestStatusId;

    @Column(name = "total_assets_requested")
    private Long totalAssetsRequested;

    @Column(name = "request_date")
    private LocalDate requestDate;

}
