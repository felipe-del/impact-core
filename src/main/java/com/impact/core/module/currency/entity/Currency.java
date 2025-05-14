package com.impact.core.module.currency.entity;

import com.impact.core.module.currency.enun.ECurrency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class that represents a currency record in the system.
 * This class maps to the 'currency' table in the database and holds information
 * about the currency code and its corresponding currency name.
 */
@Getter
@Setter
@Entity
@Table(name = "currency")
public class Currency {

    /**
     * Unique identifier for the currency.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Alphanumeric code representing the currency.
     * Limited to a maximum of 10 characters.
     */
    @Size(max = 10)
    @NotNull
    @Column(name = "currency_code", nullable = false, length = 10)
    private String currencyCode;

    /**
     * Enumerated value representing the currency name.
     * Uses the {@link ECurrency} enumeration.
     * Limited to a maximum of 50 characters.
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "currency_name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ECurrency currencyName;

}