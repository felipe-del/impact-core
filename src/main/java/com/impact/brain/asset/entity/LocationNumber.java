package com.impact.brain.asset.entity;

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

    @Column(name = "location_number")
    private Integer locationNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_type_id")
    private LocationType locationType;

}