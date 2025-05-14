package com.impact.core.module.productCategory.controller;

import com.impact.core.module.productCategory.entity.ProductCategory;
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

/**
 * Controller for handling requests related to {@link ProductCategory} entities.
 * Provides endpoints for retrieving, saving, updating, and deleting product categories.
 */
@RestController
@RequestMapping("/api/product-category")
@RequiredArgsConstructor
public class ProductCategoryController {
    public final ProductCategoryService productCategoryService;

    /**
     * Endpoint to retrieve all {@link ProductCategory} entities.
     * Requires the user to have either the 'ADMINISTRATOR' or 'MANAGER' role.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link ProductCategoryResponse} DTOs.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductCategoryResponse>>> getAllProductCategories() {
        List<ProductCategoryResponse> productCategoryResponses = productCategoryService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductCategoryResponse>>builder()
                .message("Lista de categorías de producto.")
                .data(productCategoryResponses)
                .build());
    }

    /**
     * Endpoint to save a new {@link ProductCategory}.
     * Requires the user to have either the 'ADMINISTRATOR' or 'MANAGER' role.
     *
     * @param productCategoryRequest the request body containing the {@link ProductCategory} details to be saved.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the saved {@link ProductCategoryResponse} DTO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductCategoryResponse>> saveProductCategory(@Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        ProductCategoryResponse categoryResponse = productCategoryService.save(productCategoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<ProductCategoryResponse>builder()
                .message("Categoria de producto guardada.")
                .data(categoryResponse)
                .build());
    }

    /**
     * Endpoint to update an existing {@link ProductCategory} by its id.
     * Requires the user to have either the 'ADMINISTRATOR' or 'MANAGER' role.
     *
     * @param id the id of the {@link ProductCategory} to update.
     * @param productCategoryRequest the request body containing the updated {@link ProductCategory} details.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the updated {@link ProductCategoryResponse} DTO.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductCategoryResponse>> updateProductCategory(@PathVariable int id, @Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        ProductCategoryResponse categoryResponse = productCategoryService.update(id, productCategoryRequest);

        return ResponseEntity.ok(ResponseWrapper.<ProductCategoryResponse>builder()
                .message("Categoria de producto actualizada.")
                .data(categoryResponse)
                .build());
    }

    /**
     * Endpoint to delete an existing {@link ProductCategory} by its id.
     * Requires the user to have either the 'ADMINISTRATOR' or 'MANAGER' role.
     *
     * @param id the id of the {@link ProductCategory} to delete.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the deleted {@link ProductCategoryResponse} DTO.
     */
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
