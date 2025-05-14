package com.impact.core.module.spaceRequest_Reservation.entity;

import com.impact.core.module.space.entity.Space;
import com.impact.core.module.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

/**
 * Entity class representing a space reservation for a user, including details
 * about the space, reservation times, and the user who made the reservation.
 * <p>
 * The SpaceReservation entity is used to manage the actual reservations made
 * for spaces, tracking the time the reservation starts and ends, as well as the
 * user who made the reservation.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "space_reservation")
@NoArgsConstructor
@AllArgsConstructor
public class SpaceReservation {

    /**
     * Unique identifier for the space reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * The space being reserved.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    /**
     * The start time of the reservation.
     */
    @Column(name = "start_time")
    private Instant startTime;

    /**
     * The end time of the reservation.
     */
    @Column(name = "end_time")
    private Instant endTime;

    /**
     * The {@link User} who made the reservation.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}