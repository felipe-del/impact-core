package com.impact.brain.asset.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/20/2024 - 7:38 PM
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetCategoryDTO {
    private Integer id;
    private String name;
    private Integer subcategoryId; // To hold the ID of the subcategory
}
