package com.impact.brain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SpaceReservation getReservedSpace() {
        return reservedSpace;
    }

    public void setReservedSpace(SpaceReservation reservedSpace) {
        this.reservedSpace = reservedSpace;
    }

    public TransactionType getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionType transaction) {
        this.transaction = transaction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}