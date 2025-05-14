package com.impact.core.module.assetModel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents the model of an asset in the system.
 * <p>
 * This entity defines an asset model, which includes attributes like the model name.
 * The model name is a required field and has a maximum length of 100 characters.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "asset_model")
@NoArgsConstructor
@AllArgsConstructor
public class AssetModel {

    /**
     * Unique identifier for the asset model.
     * <p>
     * This field is auto-generated with a strategy of {@link GenerationType#IDENTITY}.
     * </p>
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Name of the asset model.
     * <p>
     * This field is required and can hold up to 100 characters. It represents the
     * name of the model associated with an asset.
     * </p>
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;

}