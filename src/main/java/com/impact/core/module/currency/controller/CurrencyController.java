package com.impact.core.module.currency.controller;

import com.impact.core.module.currency.payload.response.CurrencyResponse;
import com.impact.core.module.currency.service.CurrencyService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class that handles incoming HTTP requests related to currencies.
 * Provides endpoints for retrieving currency data.
 */
@RestController
@RequestMapping("api/currency")
@RequiredArgsConstructor
public class CurrencyController {
    public final CurrencyService currencyService;

    /**
     * Retrieves a list of all available currencies in the system.
     * <p>
     * Accessible only to users with the 'ADMINISTRATOR' or 'MANAGER' role.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper}
     *         with a list of {@link CurrencyResponse} objects.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<CurrencyResponse>>> getAllCurrencies() {
        List<CurrencyResponse> currencyResponses = currencyService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<CurrencyResponse>>builder()
                .message("Lista de monedas.")
                .data(currencyResponses)
                .build());
    }

}
