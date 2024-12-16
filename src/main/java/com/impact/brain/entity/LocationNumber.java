package com.impact.brain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "location_number")
public class LocationNumber {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_type_id")
    private com.impact.brain.entity.LocationType locationType;

    @Column(name = "location_number")
    private Integer locationNumber;

}