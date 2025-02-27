package com.impact.core.module.locationNumber.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "location_number")
@NoArgsConstructor
@AllArgsConstructor
public class LocationNumber {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_type_id")
    private LocationType locationType;

    @Column(name = "location_number")
    private Integer locationNumber;

}