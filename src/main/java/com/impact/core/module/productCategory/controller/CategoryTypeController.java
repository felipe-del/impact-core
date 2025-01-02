package com.impact.core.module.productCategory.controller;

import com.impact.core.module.productCategory.payload.request.CategoryTypeRequest;
import com.impact.core.module.productCategory.payload.response.CategoryTypeResponse;
import com.impact.core.module.productCategory.service.CategoryTypeService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-type")
@RequiredArgsConstructor
public class CategoryTypeController {
    public final CategoryTypeService categoryTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<CategoryTypeResponse>>> getAllCategoryTypes() {
        List<CategoryTypeResponse> categoryTypeResponses = categoryTypeService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<CategoryTypeResponse>>builder()
                .message("Lista de tipos de categoría.")
                .data(categoryTypeResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<CategoryTypeResponse>> saveCategoryType(@Valid @RequestBody CategoryTypeRequest categoryTypeRequest) {
        CategoryTypeResponse categoryTypeResponse = categoryTypeService.save(categoryTypeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<CategoryTypeResponse>builder()
                .message("Tipo de categoría guardado.")
                .data(categoryTypeResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<CategoryTypeResponse>> updateCategoryType(@PathVariable int id, @Valid @RequestBody CategoryTypeRequest categoryTypeRequest) {
        CategoryTypeResponse categoryTypeResponse = categoryTypeService.update(id, categoryTypeRequest);

        return ResponseEntity.ok(ApiResponse.<CategoryTypeResponse>builder()
                .message("Tipo de categoría actualizado.")
                .data(categoryTypeResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<CategoryTypeResponse>> deleteCategoryType(@PathVariable int id) {
        CategoryTypeResponse categoryTypeResponse = categoryTypeService.delete(id);

        return ResponseEntity.ok(ApiResponse.<CategoryTypeResponse>builder()
                .message("Tipo de categoría eliminado.")
                .data(categoryTypeResponse)
                .build());
    }

}
