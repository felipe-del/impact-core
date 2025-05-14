package com.impact.core.module.productRequest.mapper;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.mapper.ProductMapper;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.product.service.ProductService;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productRequest.payload.request.ProductRequestDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductRequestDTOResponse;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.mapper.ResourceRequestStatusMapper;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
import com.impact.core.module.user.mapper.MyUserMapper;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link ProductRequest} entities and their corresponding Data Transfer Objects (DTOs).
 * <p>
 * This class provides methods for converting a {@link ProductRequestDTORequest} into a {@link ProductRequest} entity,
 * and vice versa, converting a {@link ProductRequest} entity into a {@link ProductRequestDTOResponse}.
 * The mapping involves integrating related entities such as {@link Product}, {@link ResourceRequestStatusResponse},
 * and {@link UserResponse}.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ProductRequestMapper {

    public final ProductService productService;
    public final ProductMapper productMapper;
    public final ResourceRequestStatusService resourceRequestStatusService;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;
    public final MyUserMapper myUserMapper;


    /**
     * Converts a {@link ProductRequestDTORequest} to a {@link ProductRequest} entity.
     * <p>
     * This method maps the product ID from the DTO to the corresponding {@link Product} entity, sets the status to
     * {@link EResourceRequestStatus#RESOURCE_REQUEST_STATUS_EARRING}, and copies the reason from the DTO.
     * </p>
     *
     * @param productRequestDTORequest the DTO containing the request details.
     * @return the corresponding {@link ProductRequest} entity.
     */
    public ProductRequest toEntity(ProductRequestDTORequest productRequestDTORequest) {
        return ProductRequest.builder()
                .product(productService.findProductById(productRequestDTORequest.getProductId()))
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING))
                .reason(productRequestDTORequest.getReason())
                .build();
    }

    /**
     * Converts a {@link ProductRequest} entity to a {@link ProductRequestDTOResponse}.
     * <p>
     * This method converts the {@link Product} and {@link ResourceRequestStatus} associated with the request
     * into their respective DTOs, and formats the creation timestamp as a string.
     * </p>
     *
     * @param productRequest the {@link ProductRequest} entity to convert.
     * @return the corresponding {@link ProductRequestDTOResponse}.
     */
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
