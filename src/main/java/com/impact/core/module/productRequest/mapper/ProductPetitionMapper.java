package com.impact.core.module.productRequest.mapper;

import com.impact.core.module.product.mapper.ProductMapper;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.product.service.ProductService;
import com.impact.core.module.productRequest.entity.ProductPetition;
import com.impact.core.module.productRequest.payload.request.ProductPetitionDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductPetitionDTOResponse;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.mapper.ResourceRequestStatusMapper;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
import com.impact.core.module.user.mapper.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPetitionMapper {

    public final ProductService productService;
    public final ProductMapper productMapper;
    public final ResourceRequestStatusService resourceRequestStatusService;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;
    public final MyUserMapper myUserMapper;

    public ProductPetition toEntity(ProductPetitionDTORequest productPetitionDTORequest) {
        return ProductPetition.builder()
                .product(productService.findProductById(productPetitionDTORequest.getProductId()))
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING))
                .reason(productPetitionDTORequest.getReason())
                .build();
    }

    public ProductPetitionDTOResponse toDTO(ProductPetition productPetition) {
        ResourceRequestStatusResponse resourceRequestStatusResponse = resourceRequestStatusMapper.toDTO(productPetition.getStatus());
        ProductResponseDTO productResponseDTO = productMapper.toDTO(productPetition.getProduct());

        return ProductPetitionDTOResponse.builder()
                .id(productPetition.getId())
                .product(productResponseDTO)
                .status(resourceRequestStatusResponse)
                .reason(productPetition.getReason())
                .user(myUserMapper.toDTO(productPetition.getUser()))
                .createdAt(productPetition.getCreatedAt().toString())
                .build();
    }

}
