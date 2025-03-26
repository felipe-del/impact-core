package com.impact.core.module.productRequestStatistics.mapper;

import com.impact.core.module.productCategory.mapper.ProductCategoryMapper;
import com.impact.core.module.productCategory.service.ProductCategoryService;
import com.impact.core.module.productRequestStatistics.entity.ProductRequestStatisticsByDate;
import com.impact.core.module.productRequestStatistics.payload.ProductRequestStatisticsByDateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRequestStatisticsByDateMapper {

    public final ProductCategoryService productCategoryService;
    public final ProductCategoryMapper productCategoryMapper;

    public ProductRequestStatisticsByDateResponse toDTO(ProductRequestStatisticsByDate productRequestStatisticsByDate) {
        return ProductRequestStatisticsByDateResponse.builder()
                .category(productCategoryMapper.toDTO(productCategoryService.findById(productRequestStatisticsByDate.getCategoryId())))
                .totalProductRequests(productRequestStatisticsByDate.getTotalProductsRequested())
                .requestDate(productRequestStatisticsByDate.getRequestDate())
                .build();
    }

}
