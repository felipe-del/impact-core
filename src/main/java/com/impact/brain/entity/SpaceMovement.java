package com.impact.brain.entity;

import com.impact.brain.commonSpace.entity.SpaceReservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "space_movements")
public class SpaceMovement {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserved_space_id")
    private SpaceReservation reservedSpace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private TransactionType transaction;

    @Column(name = "date")
    private LocalDate date;

}