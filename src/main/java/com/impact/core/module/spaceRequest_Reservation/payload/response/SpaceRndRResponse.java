package com.impact.core.module.spaceRequest_Reservation.payload.response;

import com.impact.core.module.space.payload.response.SpaceResponse;
import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.*;

import java.time.Instant;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRndRResponse {
    private SpaceResponse space;
    private int reqAndResId;
    private int numPeople;
    private String eventDesc;
    private String eventObs;
    private SpaceStatusResponse status;
    private Boolean useEquipment;
    private Instant startTime;
    private Instant endTime;
    private UserResponse user;

}
