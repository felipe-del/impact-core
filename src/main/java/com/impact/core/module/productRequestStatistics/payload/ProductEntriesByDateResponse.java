package com.impact.core.module.productRequestStatistics.payload;

import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import lombok.*;

import java.time.LocalDate;

/**
 * Response class representing the data for product entries by date.
 * <p>
 * This class is used to represent the response data for product entries, including the associated category,
 * total entries, and purchase date.
 * </p>
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntriesByDateResponse {

    /**
     * The product category associated with the product entries.
     * <p>
     * This field includes the product category information, which also includes the category type.
     * </p>
     */
    private ProductCategoryResponse category;

    /**
     * The total number of product entries for the given date.
     */
    private Long totalEntries;
    /**
     * The date when the product entries were purchased.
     */
    private LocalDate purchaseDate;
}
