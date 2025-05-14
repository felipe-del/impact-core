package com.impact.core.module.product.mapper;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.payload.request.ProductRequestDTO;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.productCategory.mapper.ProductCategoryMapper;
import com.impact.core.module.productCategory.service.ProductCategoryService;
import com.impact.core.module.productStatus.entity.ProductStatus;
import com.impact.core.module.productStatus.enun.EProductStatus;
import com.impact.core.module.productStatus.mapper.ProductStatusMapper;
import com.impact.core.module.productStatus.service.ProductStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link  Product} entities
 * and their corresponding {@link  ProductRequestDTO}
 * and {@link ProductResponseDTO} objects.
 * <p>
 * This class utilizes services and mappers for transforming product data from DTOs to entities and vice versa.
 */
@Component
@RequiredArgsConstructor
public class ProductMapper {

    public final ProductStatusService productStatusService;
    public final ProductCategoryService productCategoryService;
    public final ProductCategoryMapper productCategoryMapper;
    public final ProductStatusMapper productStatusMapper;

    /**
     * Converts a {@link ProductRequestDTO}
     * to a {@link Product} entity.
     * <p>
     * This method maps the DTO's fields to the corresponding entity's fields, including setting the product's category
     * and status based on their respective identifiers and names.
     *
     * @param productRequestDTO the DTO to be converted to an entity
     * @return the corresponding {@link Product} entity
     */
    public Product toEntity(ProductRequestDTO productRequestDTO) {
        return Product.builder()
                .purchaseDate(productRequestDTO.getPurchaseDate())
                .expiryDate(productRequestDTO.getExpiryDate() != null ? productRequestDTO.getExpiryDate() : null)
                .category(productCategoryService.findById(productRequestDTO.getCategoryId()))
                .status(this.getProductStatusByName(productRequestDTO.getStatusName()))
                .build();
    }

    /**
     * Converts a {@link Product} entity
     * to a {@link ProductResponseDTO} DTO.
     * <p>
     * This method maps the entity's fields to the corresponding DTO's fields, including transforming
     * the product's category and status into DTOs using the respective mappers.
     *
     * @param product the entity to be converted to a DTO
     * @return the corresponding {@link ProductResponseDTO} DTO
     */
    public ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .purchaseDate(product.getPurchaseDate())
                .expiryDate(product.getExpiryDate() != null ? product.getExpiryDate() : null)
                .category(productCategoryMapper.toDTO(productCategoryService.findById(product.getCategory().getId())))
                .status(productStatusMapper.toDTO(product.getStatus()))
                .build();
    }

    /**
     * Retrieves the {@link ProductStatus}
     * based on the provided status name.
     * <p>
     * This method maps status names to the corresponding {@link ProductStatus}
     * by using predefined product status enumerations.
     *
     * @param name the name of the product status
     * @return the corresponding {@link ProductStatus} entity
     */
    private ProductStatus getProductStatusByName(String name) {
        return switch (name.toLowerCase()) {
            case "loaned" -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_LOANED);
            case "earring" -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_EARRING);
            default -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_AVAILABLE);
        };
    }

}
