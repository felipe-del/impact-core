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

@RestController
@RequestMapping("/api/product-status")
@RequiredArgsConstructor
public class ProductStatusController {
    public final ProductStatusService productStatusService;

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
