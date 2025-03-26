package com.impact.core.module.productRequestStatistics.mapper;

import com.impact.core.module.productCategory.mapper.ProductCategoryMapper;
import com.impact.core.module.productCategory.service.ProductCategoryService;
import com.impact.core.module.productRequestStatistics.entity.ProductEntriesByDate;
import com.impact.core.module.productRequestStatistics.payload.ProductEntriesByDateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEntriesByDateMapper {

    public final ProductCategoryService productCategoryService;
    public final ProductCategoryMapper productCategoryMapper;

    public ProductEntriesByDateResponse toDTO(ProductEntriesByDate productEntriesByDate) {
        return ProductEntriesByDateResponse.builder()
                .category(productCategoryMapper.toDTO(productCategoryService.findById(productEntriesByDate.getCategoryId())))
                .totalEntries(productEntriesByDate.getTotalIngresos())
                .purchaseDate(productEntriesByDate.getPurchaseDate())
                .build();
    }

}
