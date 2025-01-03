package com.impact.core.module.product.payload.response;

import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import com.impact.core.module.productStatus.payload.response.ProductStatusResponse;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private int id;
    private String name;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private ProductCategoryResponse category;
    private ProductStatusResponse status;
}
