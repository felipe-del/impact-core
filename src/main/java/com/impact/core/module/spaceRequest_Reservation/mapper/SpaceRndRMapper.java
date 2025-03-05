package com.impact.core.module.spaceRequest_Reservation.mapper;

import com.impact.core.module.space.mapper.SpaceMapper;
import com.impact.core.module.space.service.SpaceService;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceReservation;
import com.impact.core.module.spaceRequest_Reservation.payload.request.SpaceRndRRequest;
import com.impact.core.module.spaceRequest_Reservation.payload.response.SpaceRndRResponse;
import com.impact.core.module.spaceStatus.mapper.SpaceStatusMapper;
import com.impact.core.module.spaceStatus.service.SpaceStatusService;
import com.impact.core.module.user.mapper.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SpaceRndRMapper {

    public final SpaceService spaceService;
    public final SpaceMapper spaceMapper;

    public final SpaceStatusService spaceStatusService;
    public final SpaceStatusMapper spaceStatusMapper;

    public final MyUserMapper myUserMapper;

        public Pair<SpaceRequest, SpaceReservation> toEntity(SpaceRndRRequest spaceRndR_Request) {
        SpaceRequest sReq = SpaceRequest.builder()
                .id(0)
                .space(spaceService.findById(spaceRndR_Request.getSpaceId()))
                .numPeople(spaceRndR_Request.getNumPeople())
                .eventDesc(spaceRndR_Request.getEventDesc())
                .eventObs(spaceRndR_Request.getEventObs())
                .status(spaceStatusService.findById(spaceRndR_Request.getStatusId()))
                .useEquipment(spaceRndR_Request.getUseEquipment())
                .build();

        SpaceReservation sRes = SpaceReservation.builder()
                .id(0)
                .space(spaceService.findById(spaceRndR_Request.getSpaceId()))
                .startTime(spaceRndR_Request.getStartTime())
                .endTime(spaceRndR_Request.getEndTime())
                .build();

        return new Pair<>(sReq, sRes);
    }

    public SpaceRndRResponse toDTO(SpaceRequest sReq, SpaceReservation sRes) {
        return SpaceRndRResponse.builder()
                .space(spaceMapper.toDTO(sReq.getSpace()))
                .numPeople(sReq.getNumPeople())
                .eventDesc(sReq.getEventDesc())
                .eventObs(sReq.getEventObs())
                .status(spaceStatusMapper.toDTO(sReq.getStatus()))
                .useEquipment(sReq.getUseEquipment())
                .startTime(sRes.getStartTime())
                .endTime(sRes.getEndTime())
                .user(myUserMapper.toDTO(sReq.getUser()))
                .build();
    }
}
