package com.impact.core.module.productRequestStatistics.repository;

import com.impact.core.module.productRequestStatistics.entity.ProductRequestStatisticsByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for accessing {@link ProductRequestStatisticsByDate} entities.
 * <p>
 * This repository extends {@link JpaRepository} to provide CRUD operations for the {@link ProductRequestStatisticsByDate}
 * entity and custom queries for product request statistics by date.
 * </p>
 */
@Repository
public interface ProductRequestStatisticsByDateRepository extends JpaRepository<ProductRequestStatisticsByDate, Integer> {

    /**
     * Finds all product request statistics within a specified date range.
     * <p>
     * This method retrieves all product request statistics from the database whose request date falls between the provided
     * start and end dates.
     * </p>
     *
     * @param startDate the start date of the range.
     * @param endDate   the end date of the range.
     * @return a list of {@link ProductRequestStatisticsByDate} entities matching the date range.
     */
    @Query("SELECT prs FROM ProductRequestStatisticsByDate prs WHERE prs.requestDate BETWEEN :start_date AND :end_date")
    List<ProductRequestStatisticsByDate> requestsInARange(@Param("start_date") LocalDate startDate,
                                                          @Param("end_date") LocalDate endDate);
}
