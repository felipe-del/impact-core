package com.impact.core.module.spaceRequest_Reservation.repository;

import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRequestRepository extends JpaRepository<SpaceRequest, Integer> {
    @Query("SELECT s FROM SpaceRequest s WHERE s.user.id = :userId")
    List<SpaceRequest> spaceRequestByUser(@Param("userId") Integer userId);

    @Query("SELECT s FROM SpaceRequest s WHERE s.status.id = :status")
    List<SpaceRequest> spaceRequestByStatus(@Param("status") Integer status);
    @Modifying
    @Query("UPDATE SpaceRequest r SET r.status.id = :statusId WHERE r.id= :requestId")
    void updateSpaceRequest(@Param("statusId") Integer statusId, @Param("requestId") Integer productId);
}
