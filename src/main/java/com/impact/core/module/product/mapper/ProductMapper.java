package com.impact.core.module.product.mapper;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.payload.request.ProductRequest;
import com.impact.core.module.product.payload.response.ProductResponse;
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

    public Product toEntity(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .purchaseDate(productRequest.getPurchaseDate())
                .expiryDate(productRequest.getExpiryDate())
                .category(productCategoryService.findById(productRequest.getCategoryId()))
                .status(this.getProductStatus(productRequest.getStatusName()))
                .build();
    }

    public ProductResponse toDTO(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .purchaseDate(product.getPurchaseDate())
                .expiryDate(product.getExpiryDate())
                .category(productCategoryMapper.toDTO(productCategoryService.findById(product.getCategory().getId())))
                .status(productStatusMapper.toDTO(product.getStatus()))
                .build();
    }

    // PRIVATE METHODS

    private ProductStatus getProductStatus(String statusName) {
        return switch (statusName.toLowerCase()) {
            case "pendiente" -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_EARRING);
            case "prestado" -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_LOANED);
            default -> productStatusService.findByName(EProductStatus.PRODUCT_STATUS_AVAILABLE);
        };
    }
}
