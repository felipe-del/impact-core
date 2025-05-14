package com.impact.core.module.assetSubcategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entity class representing an {@link AssetCategory}.
 * <p>
 * This class defines an asset category, which is used to categorize assets in the system. It contains the category's
 * unique identifier and its name, which must be provided and is constrained to a maximum length of 100 characters.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "asset_category")
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategory {

    /**
     * The unique identifier of the {@link AssetCategory}.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the {@link AssetCategory}.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

}