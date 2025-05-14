package com.impact.core.module.productRequestStatistics.controller;

import com.impact.core.module.productRequestStatistics.payload.ProductEntriesByDateResponse;
import com.impact.core.module.productRequestStatistics.service.ProductEntriesByDateService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for handling requests related to product entries by date.
 * <p>
 * This controller exposes endpoints to retrieve the total product entries (ingresos) by date, either for all data or for a specific date range.
 * It also ensures that only users with the appropriate roles (ADMINISTRATOR or MANAGER) can access the data.
 * </p>
 */
@RestController
@RequestMapping("/api/product-entries")
@RequiredArgsConstructor
public class ProductEntriesByDateController {
    public final ProductEntriesByDateService productEntriesByDateService;

    /**
     * Retrieves all product entries (ingresos) by date.
     * <p>
     * This endpoint fetches all product entries and returns them in a response wrapped inside a {@link ResponseWrapper}.
     * It requires the user to have either the ADMINISTRATOR or MANAGER role to access the data.
     * </p>
     *
     * @return a {@link ResponseEntity} containing the wrapped list of {@link ProductEntriesByDateResponse}.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductEntriesByDateResponse>>> getAllProductEntries() {
        List<ProductEntriesByDateResponse> productEntriesByDateResponses = productEntriesByDateService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductEntriesByDateResponse>>builder()
                .message("Total de ingresos de productos por fechas")
                .data(productEntriesByDateResponses)
                .build());
    }

    /**
     * Retrieves product entries by date within a specific range.
     * <p>
     * This endpoint fetches all product entries between the provided start and end date, and returns the data wrapped inside
     * a {@link ResponseWrapper}. It requires the user to have either the ADMINISTRATOR or MANAGER role to access the data.
     * </p>
     *
     * @param startDate the start date of the range.
     * @param endDate the end date of the range.
     * @return a {@link ResponseEntity} containing the wrapped list of {@link ProductEntriesByDateResponse}.
     */
    @GetMapping("/range")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductEntriesByDateResponse>>>
    getAllProductEntriesInARange(@RequestParam("start_date") LocalDate startDate,
                                 @RequestParam("end_date") LocalDate endDate) {
        List<ProductEntriesByDateResponse> productEntriesByDateResponses = productEntriesByDateService.entriesByDate(startDate, endDate);

        return ResponseEntity.ok(ResponseWrapper.<List<ProductEntriesByDateResponse>>builder()
                .message("Total de ingresos de productos entre " + startDate + " y " + endDate)
                .data(productEntriesByDateResponses)
                .build());
    }
}
