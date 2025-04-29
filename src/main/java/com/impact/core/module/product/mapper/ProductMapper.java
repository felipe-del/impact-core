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

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public final ProductStatusService productStatusService;
    public final ProductCategoryService productCategoryService;
    public final ProductCategoryMapper productCategoryMapper;
    public final ProductStatusMapper productStatusMapper;

    public Product toEntity(ProductRequestDTO productRequestDTO) {
        return Product.builder()
                .purchaseDate(productRequestDTO.getPurchaseDate())
                .expiryDate(productRequestDTO.getExpiryDate() != null ? productRequestDTO.getExpiryDate() : null)
                .category(productCategoryService.findById(productRequestDTO.getCategoryId()))
                .status(this.getProductStatusByName(productRequestDTO.getStatusName()))
                .build();
    }

    public ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .purchaseDate(product.getPurchaseDate())
                .expiryDate(product.getExpiryDate() != null ? product.getExpiryDate() : null)
                .category(productCategoryMapper.toDTO(productCategoryService.findById(product.getCategory().getId())))
                .status(productStatusMapper.toDTO(product.getStatus()))
                .build();
    }

    private ProductStatus getProductStatusByName(String name) {
        return switch (name.toLowerCase()) {
            case "loaned" -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_LOANED);
            case "earring" -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_EARRING);
            default -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_AVAILABLE);
        };
    }

}
