package com.impact.core.module.productCategory.controller;

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

@RestController
@RequestMapping("/api/category-type")
@RequiredArgsConstructor
public class CategoryTypeController {
    public final CategoryTypeService categoryTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<CategoryTypeResponse>>> getAllCategoryTypes() {
        List<CategoryTypeResponse> categoryTypeResponses = categoryTypeService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<CategoryTypeResponse>>builder()
                .message("Lista de tipos de categoría.")
                .data(categoryTypeResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<CategoryTypeResponse>> saveCategoryType(@Valid @RequestBody CategoryTypeRequest categoryTypeRequest) {
        CategoryTypeResponse categoryTypeResponse = categoryTypeService.save(categoryTypeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<CategoryTypeResponse>builder()
                .message("Tipo de categoría guardado.")
                .data(categoryTypeResponse)
                .build());
    }

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
