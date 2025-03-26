package com.impact.core.module.productRequestStatistics.payload;

import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntriesByDateResponse {
    private ProductCategoryResponse category; // this response also includes the category type
    private Long totalEntries;
    private LocalDate purchaseDate;
}
