package com.impact.core.module.productCategory.controller;

import com.impact.core.module.productCategory.entity.CategoryType;
import com.impact.core.module.productCategory.payload.request.CategoryTypeRequest;
import com.impact.core.module.productCategory.payload.response.CategoryTypeResponse;
import com.impact.core.module.productCategory.service.CategoryTypeService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling requests related to {@link CategoryType} entities.
 * Provides endpoints for retrieving, saving, updating, and deleting category types.
 */
@RestController
@RequestMapping("/api/product-category-type")
@RequiredArgsConstructor
public class ProductCategoryTypeController {
    public final CategoryTypeService categoryTypeService;

    /**
     * Endpoint to retrieve all {@link CategoryType} entities.
     * Requires the user to have either the 'ADMINISTRATOR' or 'MANAGER' role.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link CategoryTypeResponse} DTOs.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<CategoryTypeResponse>>> getAllCategoryTypes() {
        List<CategoryTypeResponse> categoryTypeResponses = categoryTypeService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<CategoryTypeResponse>>builder()
                .message("Lista de tipos de categoría.")
                .data(categoryTypeResponses)
                .build());
    }

    /**
     * Endpoint to save a new {@link CategoryType}.
     * Requires the user to have either the 'ADMINISTRATOR' or 'MANAGER' role.
     *
     * @param categoryTypeRequest the request body containing the {@link CategoryType} details to be saved.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the saved {@link CategoryTypeResponse} DTO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<CategoryTypeResponse>> saveCategoryType(@Valid @RequestBody CategoryTypeRequest categoryTypeRequest) {
        CategoryTypeResponse categoryTypeResponse = categoryTypeService.save(categoryTypeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<CategoryTypeResponse>builder()
                .message("Tipo de categoría guardado.")
                .data(categoryTypeResponse)
                .build());
    }

    /**
     * Endpoint to update an existing {@link CategoryType} by its id.
     * Requires the user to have either the 'ADMINISTRATOR' or 'MANAGER' role.
     *
     * @param id the id of the {@link CategoryType} to update.
     * @param categoryTypeRequest the request body containing the updated {@link CategoryType} details.
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the updated {@link CategoryTypeResponse} DTO.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<CategoryTypeResponse>> updateCategoryType(@PathVariable int id, @Valid @RequestBody CategoryTypeRequest categoryTypeRequest) {
        CategoryTypeResponse categoryTypeResponse = categoryTypeService.update(id, categoryTypeRequest);

        return ResponseEntity.ok(ResponseWrapper.<CategoryTypeResponse>builder()
                .message("Tipo de categoría actualizado.")
                .data(categoryTypeResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<CategoryTypeResponse>> deleteCategoryType(@PathVariable int id) {
        CategoryTypeResponse categoryTypeResponse = categoryTypeService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<CategoryTypeResponse>builder()
                .message("Tipo de categoría eliminado.")
                .data(categoryTypeResponse)
                .build());
    }

}
