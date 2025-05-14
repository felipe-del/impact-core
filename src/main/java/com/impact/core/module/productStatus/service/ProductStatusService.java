package com.impact.core.module.productStatus.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.productStatus.entity.ProductStatus;
import com.impact.core.module.productStatus.enun.EProductStatus;
import com.impact.core.module.productStatus.mapper.ProductStatusMapper;
import com.impact.core.module.productStatus.payload.response.ProductStatusResponse;
import com.impact.core.module.productStatus.repository.ProductStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling the business logic related to product statuses.
 * <p>
 * This service provides methods for retrieving product status information from the repository
 * and converting it into the appropriate data transfer objects (DTOs) for use by controllers.
 * </p>
 */
@Service("productStatusService")
@RequiredArgsConstructor
public class ProductStatusService {
    public final ProductStatusRepository productStatusRepository;
    public final ProductStatusMapper productStatusMapper;

    /**
     * Retrieves a ProductStatus entity by its name (status).
     * <p>
     * If the ProductStatus is not found in the database, a ResourceNotFoundException is thrown.
     * </p>
     *
     * @param name The name of the ProductStatus to retrieve (an enum value of EProductStatus).
     * @return The ProductStatus entity.
     * @throws ResourceNotFoundException if no matching ProductStatus is found.
     */
    public ProductStatus findByName(EProductStatus name) {
        return productStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("El estado: " + name + " no existe en la base de datos."));
    }

    /**
     * Retrieves all product statuses from the database and converts them into DTOs.
     * <p>
     * This method fetches all ProductStatus entities and maps them to ProductStatusResponse DTOs
     * for easier use in the frontend layer.
     * </p>
     *
     * @return A list of ProductStatusResponse DTOs representing all product statuses.
     */
    public List<ProductStatusResponse> findAll() {
        return productStatusRepository.findAll().stream()
                .map(productStatusMapper::toDTO)
                .collect(Collectors.toList());
    }
}
