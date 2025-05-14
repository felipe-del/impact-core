package com.impact.core.module.assetSubcategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entity class representing an {@link AssetSubcategory}.
 * <p>
 * This class defines an asset subcategory, which is used to further categorize assets within an {@link AssetCategory}.
 * It contains the subcategory's unique identifier, name, description, and the associated {@link AssetCategory}.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "asset_subcategory")
@NoArgsConstructor
@AllArgsConstructor
public class AssetSubcategory {

    /**
     * The unique identifier of the {@link AssetSubcategory}.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * The name of the {@link AssetSubcategory}.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;


    /**
     * A description of the {@link AssetSubcategory}.
     */
    @Size(max = 255)
    @Column(name = "description")
    private String description;

    /**
     * The {@link AssetCategory} to which this {@link AssetSubcategory} belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private AssetCategory category;

}