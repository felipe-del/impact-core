package com.impact.core.module.assetStatus.entity;

import com.impact.core.module.assetStatus.enun.EAssetStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing the status of an asset in the system.
 * It includes information about the asset's current status and an optional description.
 */
@Getter
@Setter
@Entity
@Table(name = "asset_status")
public class AssetStatus {

    /**
     * The unique identifier for the asset status.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * The name of the asset status.
     * It is an enumeration value from the {@link EAssetStatus} enum and must not exceed 50 characters.
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EAssetStatus name;

    /**
     * A detailed description of the asset status.
     */
    @Lob
    @Column(name = "description")
    private String description;

}