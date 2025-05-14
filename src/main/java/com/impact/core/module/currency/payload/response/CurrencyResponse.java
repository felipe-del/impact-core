package com.impact.core.module.currency.payload.response;

import lombok.*;

/**
 * Data Transfer Object (DTO) that represents the essential information of a currency.
 * <p>
 * This class is used for returning currency details in API responses.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {

    /**
     * Unique identifier of the currency.
     */
    private Integer id;

    /**
     * Standard code of the currency.
     */
    private String code;

    /**
     * Symbol associated with the currency
     */
    private String symbol;

    /**
     * State name of the currency derived from the enumeration value.
     */
    private String stateName;
}
