package com.impact.core.module.productRequestStatistics.mapper;

import com.impact.core.module.productCategory.mapper.ProductCategoryMapper;
import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import com.impact.core.module.productCategory.service.ProductCategoryService;
import com.impact.core.module.productRequestStatistics.entity.ProductEntriesByDate;
import com.impact.core.module.productRequestStatistics.payload.ProductEntriesByDateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting {@link ProductEntriesByDate} entities to {@link ProductEntriesByDateResponse} DTOs.
 * <p>
 * This class is responsible for transforming the data from the {@link ProductEntriesByDate} entity to a more
 * user-friendly {@link ProductEntriesByDateResponse} DTO. It makes use of the {@link ProductCategoryService} and
 * {@link ProductCategoryMapper} to retrieve and map product category data.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ProductEntriesByDateMapper {

    public final ProductCategoryService productCategoryService;
    public final ProductCategoryMapper productCategoryMapper;

    /**
     * Converts a {@link ProductEntriesByDate} entity to a {@link ProductEntriesByDateResponse} DTO.
     * <p>
     * This method takes a {@link ProductEntriesByDate} entity, retrieves the associated product category
     * using the {@link ProductCategoryService}, maps it to a {@link ProductCategoryResponse} DTO via
     * {@link ProductCategoryMapper}, and returns a {@link ProductEntriesByDateResponse} with the relevant data.
     * </p>
     *
     * @param productEntriesByDate the {@link ProductEntriesByDate} entity to be converted.
     * @return the mapped {@link ProductEntriesByDateResponse} DTO.
     */
    public ProductEntriesByDateResponse toDTO(ProductEntriesByDate productEntriesByDate) {
        return ProductEntriesByDateResponse.builder()
                .category(productCategoryMapper.toDTO(productCategoryService.findById(productEntriesByDate.getCategoryId())))
                .totalEntries(productEntriesByDate.getTotalIngresos())
                .purchaseDate(productEntriesByDate.getPurchaseDate())
                .build();
    }

}
