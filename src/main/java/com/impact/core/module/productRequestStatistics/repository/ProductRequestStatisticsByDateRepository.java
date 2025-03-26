package com.impact.core.module.productRequestStatistics.repository;

import com.impact.core.module.productRequestStatistics.entity.ProductRequestStatisticsByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRequestStatisticsByDateRepository extends JpaRepository<ProductRequestStatisticsByDate, Integer> {
    @Query("SELECT prs FROM ProductRequestStatisticsByDate prs WHERE prs.requestDate BETWEEN :start_date AND :end_date")
    List<ProductRequestStatisticsByDate> requestsInARange(@Param("start_date") LocalDate startDate,
                                                          @Param("end_date") LocalDate endDate);
}
