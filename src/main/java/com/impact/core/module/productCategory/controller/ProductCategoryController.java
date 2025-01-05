package com.impact.core.module.productCategory.controller;

import com.impact.core.module.productCategory.payload.request.ProductCategoryRequest;
import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import com.impact.core.module.productCategory.service.ProductCategoryService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-category")
@RequiredArgsConstructor
public class ProductCategoryController {
    public final ProductCategoryService productCategoryService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductCategoryResponse>>> getAllProductCategories() {
        List<ProductCategoryResponse> productCategoryResponses = productCategoryService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductCategoryResponse>>builder()
                .message("Lista de categorías de producto.")
                .data(productCategoryResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductCategoryResponse>> saveProductCategory(@Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        ProductCategoryResponse categoryResponse = productCategoryService.save(productCategoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<ProductCategoryResponse>builder()
                .message("Categoria de producto guardada.")
                .data(categoryResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductCategoryResponse>> updateProductCategory(@PathVariable int id, @Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        ProductCategoryResponse categoryResponse = productCategoryService.update(id, productCategoryRequest);

        return ResponseEntity.ok(ResponseWrapper.<ProductCategoryResponse>builder()
                .message("Categoria de producto actualizada.")
                .data(categoryResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductCategoryResponse>> deleteProductCategory(@PathVariable int id) {
        ProductCategoryResponse categoryResponse = productCategoryService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<ProductCategoryResponse>builder()
                .message("Categoria de producto eliminada.")
                .data(categoryResponse)
                .build());
    }
}
