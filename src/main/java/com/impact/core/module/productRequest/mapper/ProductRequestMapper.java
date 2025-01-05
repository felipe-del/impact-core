package com.impact.core.module.productRequest.mapper;

import com.impact.core.module.product.mapper.ProductMapper;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.product.service.ProductService;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productRequest.payload.request.ProductRequestDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductRequestDTOResponse;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.mapper.ResourceRequestStatusMapper;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
import com.impact.core.module.user.mapper.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRequestMapper {

    public final ProductService productService;
    public final ProductMapper productMapper;
    public final ResourceRequestStatusService resourceRequestStatusService;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;
    public final MyUserMapper myUserMapper;

    public ProductRequest toEntity(ProductRequestDTORequest productRequestDTORequest) {
        return ProductRequest.builder()
                .product(productService.findProductById(productRequestDTORequest.getProductId()))
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING))
                .reason(productRequestDTORequest.getReason())
                .build();
    }

    public ProductRequestDTOResponse toDTO(ProductRequest productRequest) {
        ResourceRequestStatusResponse resourceRequestStatusResponse = resourceRequestStatusMapper.toDTO(productRequest.getStatus());
        ProductResponseDTO productResponseDTO = productMapper.toDTO(productRequest.getProduct());

        return ProductRequestDTOResponse.builder()
                .id(productRequest.getId())
                .product(productResponseDTO)
                .status(resourceRequestStatusResponse)
                .reason(productRequest.getReason())
                .user(myUserMapper.toDTO(productRequest.getUser()))
                .createdAt(productRequest.getCreatedAt().toString())
                .build();
    }

}
