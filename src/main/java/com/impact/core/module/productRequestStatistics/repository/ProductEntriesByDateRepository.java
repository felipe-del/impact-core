package com.impact.core.module.productRequestStatistics.repository;

import com.impact.core.module.productRequestStatistics.entity.ProductEntriesByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductEntriesByDateRepository extends JpaRepository<ProductEntriesByDate, Integer> {
    @Query("SELECT pe FROM ProductEntriesByDate pe WHERE pe.purchaseDate BETWEEN :start_date AND :end_date")
    List<ProductEntriesByDate> entriesInARange(@Param("start_date") LocalDate startDate,
                                               @Param("end_date") LocalDate endDate);
}
