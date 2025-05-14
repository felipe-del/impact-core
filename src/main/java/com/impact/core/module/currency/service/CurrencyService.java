package com.impact.core.module.currency.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.enun.ECurrency;
import com.impact.core.module.currency.mapper.CurrencyMapper;
import com.impact.core.module.currency.payload.response.CurrencyResponse;
import com.impact.core.module.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for business logic related to the Currency entity.
 * <p>
 * Provides methods to retrieve Currency data either by its enumeration name or as a full list,
 * converting the results to their respective Data Transfer Object (DTO) representations when required.
 */
@Service("currencyService")
@RequiredArgsConstructor
public class CurrencyService {
    public final CurrencyRepository currencyRepository;
    public final CurrencyMapper currencyMapper;

    /**
     * Retrieves a Currency entity based on its currency name enumeration.
     *
     * @param currencyName The enumeration value of the currency (e.g., CURRENCY_COLON, CURRENCY_DOLLAR).
     * @return The corresponding Currency entity.
     * @throws ResourceNotFoundException If the currency name does not exist in the database.
     */
    public Currency findByCurrencyName(ECurrency currencyName) {
        return currencyRepository.findByCurrencyName(currencyName)
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de moneda : " + currencyName + " no se encuentra en la base de datos"));
    }

    /**
     * Retrieves all Currency entities from the database and converts them into their corresponding
     * Data Transfer Object (DTO) representations.
     *
     * @return A list of CurrencyResponse Data Transfer Objects (DTOs).
     */
    public List<CurrencyResponse> findAll() {
        return currencyRepository.findAll().stream()
                .map(currencyMapper::toDTO)
                .toList();
    }
}
