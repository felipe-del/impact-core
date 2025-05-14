package com.impact.core.module.productRequestStatistics.repository;

import com.impact.core.module.productRequestStatistics.entity.ProductEntriesByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for accessing {@link ProductEntriesByDate} entities.
 * <p>
 * This repository provides methods for querying the product entries by date. It extends the {@link JpaRepository}
 * to perform CRUD operations on the {@link ProductEntriesByDate} entity.
 * </p>
 */
@Repository
public interface ProductEntriesByDateRepository extends JpaRepository<ProductEntriesByDate, Integer> {

    /**
     * Finds all product entries within a specified date range.
     * <p>
     * This method retrieves all product entries from the database whose purchase date falls between the provided
     * start and end dates.
     * </p>
     *
     * @param startDate the start date of the range.
     * @param endDate   the end date of the range.
     * @return a list of {@link ProductEntriesByDate} entities matching the date range.
     */
    @Query("SELECT pe FROM ProductEntriesByDate pe WHERE pe.purchaseDate BETWEEN :start_date AND :end_date")
    List<ProductEntriesByDate> entriesInARange(@Param("start_date") LocalDate startDate,
                                               @Param("end_date") LocalDate endDate);
}
