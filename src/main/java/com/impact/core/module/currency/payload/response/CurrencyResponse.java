package com.impact.core.module.currency.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {
    private Integer id;
    private String code;
    private String symbol;
    private String stateName;
}
