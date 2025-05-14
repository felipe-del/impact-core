package com.impact.core.module.currency.repository;

import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.enun.ECurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing persistence and retrieval operations
 * related to the Currency entity.
 * <p>
 * Extends JpaRepository to provide standard Create, Read, Update, and Delete (CRUD)
 * operations and includes custom query methods as needed.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    /**
     * Retrieves a Currency entity by its associated currency name enumeration value.
     *
     * @param currencyName The currency name represented by the ECurrency enumeration.
     * @return An Optional containing the Currency entity if found, or empty otherwise.
     */
    Optional<Currency> findByCurrencyName(ECurrency currencyName);
}
