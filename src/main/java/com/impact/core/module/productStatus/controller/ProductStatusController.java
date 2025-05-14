package com.impact.core.module.productStatus.controller;

import com.impact.core.module.productStatus.payload.response.ProductStatusResponse;
import com.impact.core.module.productStatus.service.ProductStatusService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for handling requests related to product status.
 * <p>
 * This controller exposes endpoints for retrieving information about the status of products.
 * Only users with the 'ADMINISTRATOR' or 'MANAGER' roles are authorized to access these endpoints.
 * </p>
 */
@RestController
@RequestMapping("/api/product-status")
@RequiredArgsConstructor
public class ProductStatusController {
    public final ProductStatusService productStatusService;

    /**
     * Endpoint to retrieve all product statuses.
     * <p>
     * This endpoint returns a list of all product statuses in the system. Only users with 'ADMINISTRATOR'
     * or 'MANAGER' roles can access this endpoint.
     * </p>
     *
     * @return a ResponseEntity containing a list of ProductStatusResponse objects and a message.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductStatusResponse>>> getAllProductStatus() {
        List<ProductStatusResponse> productStatusResponses = productStatusService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductStatusResponse>>builder()
                .message("Lista de estados de producto.")
                .data(productStatusResponses)
                .build());
    }
}
