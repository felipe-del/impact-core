package com.impact.core.module.assetStatus.entity;

import com.impact.core.module.assetStatus.enun.EAssetStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "asset_status")
public class AssetStatus {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EAssetStatus name;

    @Lob
    @Column(name = "description")
    private String description;

}