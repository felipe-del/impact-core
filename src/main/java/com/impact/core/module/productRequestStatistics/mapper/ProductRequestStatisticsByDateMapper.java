package com.impact.core.module.productRequestStatistics.mapper;

import com.impact.core.module.productCategory.mapper.ProductCategoryMapper;
import com.impact.core.module.productCategory.service.ProductCategoryService;
import com.impact.core.module.productRequestStatistics.entity.ProductRequestStatisticsByDate;
import com.impact.core.module.productRequestStatistics.payload.ProductRequestStatisticsByDateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting {@link ProductRequestStatisticsByDate} entities to {@link ProductRequestStatisticsByDateResponse} DTOs.
 * <p>
 * This component is responsible for mapping {@link ProductRequestStatisticsByDate} entities, which represent product request statistics
 * by date, into {@link ProductRequestStatisticsByDateResponse} DTOs that are used for data transfer.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ProductRequestStatisticsByDateMapper {

    public final ProductCategoryService productCategoryService;
    public final ProductCategoryMapper productCategoryMapper;

    /**
     * Converts a {@link ProductRequestStatisticsByDate} entity into a {@link ProductRequestStatisticsByDateResponse} DTO.
     * <p>
     * This method takes a {@link ProductRequestStatisticsByDate} entity and maps it into a {@link ProductRequestStatisticsByDateResponse}
     * DTO. The conversion includes fetching the related product category using the {@link ProductCategoryService} and
     * mapping it using {@link ProductCategoryMapper}.
     * </p>
     *
     * @param productRequestStatisticsByDate the entity representing product request statistics by date.
     * @return a {@link ProductRequestStatisticsByDateResponse} DTO representing the product request statistics.
     */
    public ProductRequestStatisticsByDateResponse toDTO(ProductRequestStatisticsByDate productRequestStatisticsByDate) {
        return ProductRequestStatisticsByDateResponse.builder()
                .category(productCategoryMapper.toDTO(productCategoryService.findById(productRequestStatisticsByDate.getCategoryId())))
                .totalProductRequests(productRequestStatisticsByDate.getTotalProductsRequested())
                .requestDate(productRequestStatisticsByDate.getRequestDate())
                .build();
    }

}
