package com.impact.core.module.productRequest.controller;

import com.impact.core.module.productRequest.payload.request.ProductRequestDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductRequestDTOResponse;
import com.impact.core.module.productRequest.service.ProductRequestService;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-request")
@RequiredArgsConstructor
public class ProductRequestController {
    public final ProductRequestService productRequestService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<ProductRequestDTOResponse>>> getAllProductRequests() {
        List<ProductRequestDTOResponse> productRequestDTOResponses = productRequestService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductRequestDTOResponse>>builder()
                .message("Lista de solicitudes de productos.")
                .data(productRequestDTOResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductRequestDTOResponse>> saveProductRequest(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ProductRequestDTORequest productRequestDTORequest) {
        ProductRequestDTOResponse ProductRequestDTOResponse = productRequestService.save(userDetails, productRequestDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<ProductRequestDTOResponse>builder()
                .message("Solicitud de producto guardada correctamente.")
                .data(ProductRequestDTOResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductRequestDTOResponse>> updateProductRequest(
            @PathVariable int id, @Valid @RequestBody ProductRequestDTORequest productRequestDTORequest) {
        ProductRequestDTOResponse ProductRequestDTOResponse = productRequestService.update(id, productRequestDTORequest);

        return ResponseEntity.ok(ResponseWrapper.<ProductRequestDTOResponse>builder()
                .message("Solicitud de producto actualizada correctamente.")
                .data(ProductRequestDTOResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductRequestDTOResponse>> deleteProductRequest(@PathVariable int id) {
        ProductRequestDTOResponse ProductRequestDTOResponse = productRequestService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<ProductRequestDTOResponse>builder()
                .message("Solicitud de producto eliminada correctamente.")
                .data(ProductRequestDTOResponse)
                .build());
    }
}
