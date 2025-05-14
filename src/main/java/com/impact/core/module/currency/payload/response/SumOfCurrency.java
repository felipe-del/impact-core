package com.impact.core.module.currency.payload.response;

import lombok.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) used to encapsulate the total amount and quantity of a specific currency.
 * <p>
 * This class is useful for representing aggregate data grouped by currency, such as total balances or quantities.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SumOfCurrency {

    /**
     * Data Transfer Object (DTO) representing the currency type (e.g., colon or dollar).
     */
    private CurrencyResponse currency;

    /**
     * The total count or quantity of items or transactions associated with this currency.
     */
    private Long quantity;

    /**
     * The total monetary amount summed for the given currency.
     */
    private BigDecimal amount;
}
