package com.impact.brain.commonSpace.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SpaceRequestInformationDTO {
    private Integer spaceId;      // Information needed for both
    private Integer numPeople;    // Information needed for space request
    private String eventDesc;     // Information needed for space request
    private Integer statusId;     // Information needed for space request
    private Boolean useEquipment; // Information needed for space request
    private Instant startTime;  // Information needed for space reservation
    private Instant endTime;    // Information needed for space reservation

    @Override
    public String toString() {
        return "SpaceRequestDTO{" +
                ", spaceId=" + spaceId +
                ", numPeople=" + numPeople +
                ", eventDesc=" + eventDesc +
                ", statusId=" + statusId +
                '}';
    }
}
