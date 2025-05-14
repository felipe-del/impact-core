package com.impact.core.module.productRequestStatistics.controller;

import com.impact.core.module.productRequestStatistics.payload.ProductRequestStatisticsByDateResponse;
import com.impact.core.module.productRequestStatistics.service.ProductRequestStatisticsByDateService;
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
 * Controller for handling requests related to product request statistics by date.
 * <p>
 * This controller exposes endpoints to retrieve product request statistics by date, either for all data or for a specific date range.
 * It ensures that only users with the appropriate roles (ADMINISTRATOR or MANAGER) can access the data.
 * </p>
 */
@RestController
@RequestMapping("/api/product-request-statistics")
@RequiredArgsConstructor
public class ProductRequestStatisticsByDateController {

    private final ProductRequestStatisticsByDateService productRequestStatisticsByDateService;

    /**
     * Retrieves all product request statistics by date.
     * <p>
     * This endpoint fetches all product request statistics by date and returns them in a response wrapped inside a
     * {@link ResponseWrapper}. It requires the user to have either the ADMINISTRATOR or MANAGER role to access the data.
     * </p>
     *
     * @return a {@link ResponseEntity} containing the wrapped list of {@link ProductRequestStatisticsByDateResponse}.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductRequestStatisticsByDateResponse>>> getAllProductRequestStatistics() {
        List<ProductRequestStatisticsByDateResponse> requestStatisticsResponses = productRequestStatisticsByDateService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductRequestStatisticsByDateResponse>>builder()
                .message("Lista de estadísticas de solicitudes de productos")
                .data(requestStatisticsResponses)
                .build());
    }

    /**
     * Retrieves product request statistics by date within a specific range.
     * <p>
     * This endpoint fetches product request statistics by date between the provided start and end date and returns the
     * data wrapped inside a {@link ResponseWrapper}. It requires the user to have either the ADMINISTRATOR or MANAGER role
     * to access the data.
     * </p>
     *
     * @param startDate the start date of the range.
     * @param endDate the end date of the range.
     * @return a {@link ResponseEntity} containing the wrapped list of {@link ProductRequestStatisticsByDateResponse}.
     */
    @GetMapping("/range")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductRequestStatisticsByDateResponse>>>
    getAllProductRequestStatisticsInARange(@RequestParam("start_date") LocalDate startDate,
                                           @RequestParam("end_date") LocalDate endDate) {
        List<ProductRequestStatisticsByDateResponse> requestStatisticsByDateResponses =
                productRequestStatisticsByDateService.requestsStatisticsInARange(startDate, endDate);

        return ResponseEntity.ok(ResponseWrapper.<List<ProductRequestStatisticsByDateResponse>>builder()
                .message("Estadísticas de solicitudes entre " + startDate + " y " + endDate)
                .data(requestStatisticsByDateResponses)
                .build());
    }

}
