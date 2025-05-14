package com.impact.core.module.productRequestStatistics.payload;

import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import lombok.*;

import java.time.LocalDate;

/**
 * Response class representing the statistics for product requests by date.
 * <p>
 * This class is used to represent the response data for product request statistics, including the associated category,
 * total product requests, and request date.
 * </p>
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestStatisticsByDateResponse {

    /**
     * The product category associated with the product request statistics.
     * <p>
     * This field includes the product category information, which also includes the category type.
     * </p>
     */
    private ProductCategoryResponse category;

    /**
     * The total number of product requests for the given date.
     */
    private Long totalProductRequests;

    /**
     * The date when the product requests were made.
     */
    private LocalDate requestDate;
}
