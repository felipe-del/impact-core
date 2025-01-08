package com.impact.core.module.productRequest.controller;

import com.impact.core.module.productRequest.payload.request.ProductPetitionDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductPetitionDTOResponse;
import com.impact.core.module.productRequest.service.ProductPetitionService;
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
public class ProductPetitionController {
    public final ProductPetitionService productPetitionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<ProductPetitionDTOResponse>>> getAllProductRequests() {
        List<ProductPetitionDTOResponse> productPetitionDTORespons = productPetitionService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductPetitionDTOResponse>>builder()
                .message("Lista de solicitudes de productos.")
                .data(productPetitionDTORespons)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductPetitionDTOResponse>> saveProductRequest(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ProductPetitionDTORequest productPetitionDTORequest) {
        ProductPetitionDTOResponse ProductPetitionDTOResponse = productPetitionService.save(userDetails, productPetitionDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<ProductPetitionDTOResponse>builder()
                .message("Solicitud de producto guardada correctamente.")
                .data(ProductPetitionDTOResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductPetitionDTOResponse>> updateProductRequest(
            @PathVariable int id, @Valid @RequestBody ProductPetitionDTORequest productPetitionDTORequest) {
        ProductPetitionDTOResponse ProductPetitionDTOResponse = productPetitionService.update(id, productPetitionDTORequest);

        return ResponseEntity.ok(ResponseWrapper.<ProductPetitionDTOResponse>builder()
                .message("Solicitud de producto actualizada correctamente.")
                .data(ProductPetitionDTOResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductPetitionDTOResponse>> deleteProductRequest(@PathVariable int id) {
        ProductPetitionDTOResponse ProductPetitionDTOResponse = productPetitionService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<ProductPetitionDTOResponse>builder()
                .message("Solicitud de producto eliminada correctamente.")
                .data(ProductPetitionDTOResponse)
                .build());
    }
}
