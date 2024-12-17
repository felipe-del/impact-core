package com.impact.core.entities;

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
    private com.impact.core.entities.LocationType locationType;

    @Column(name = "location_number")
    private Integer locationNumber;

}