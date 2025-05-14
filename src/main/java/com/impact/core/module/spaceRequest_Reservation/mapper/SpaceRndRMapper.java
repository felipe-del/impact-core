package com.impact.core.module.spaceRequest_Reservation.mapper;

import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.mapper.ResourceRequestStatusMapper;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
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

/**
 * Mapper class responsible for converting between Space Request and Space Reservation entities and their respective DTOs.
 * This class also converts incoming request data (SpaceRndRRequest) into entities and prepares response data (SpaceRndRResponse)
 * for outgoing responses.
 */
@Component
@RequiredArgsConstructor
public class SpaceRndRMapper {

    public final SpaceService spaceService;
    public final SpaceMapper spaceMapper;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;
    public final ResourceRequestStatusService resourceRequestStatusService;
    public final MyUserMapper myUserMapper;

   /**
    * Converts a {SpaceRndRRequest} object into SpaceRequest and SpaceReservation entities.
    * <p>
    * This method retrieves the relevant space and resource request status for the request and creates a new
    * SpaceRequest and SpaceReservation.
    *
    * @param spaceRndR_Request The {@link SpaceRndRRequest} object containing the request data.
    * @return A {@link Pair} containing the {@link SpaceRequest} and {@link SpaceReservation} entities.
    */
    public Pair<SpaceRequest, SpaceReservation> toEntity(SpaceRndRRequest spaceRndR_Request) {
        SpaceRequest sReq = SpaceRequest.builder()
                .id(0)
                .space(spaceService.findById(spaceRndR_Request.getSpaceId()))
                .numPeople(spaceRndR_Request.getNumPeople())
                .eventDesc(spaceRndR_Request.getEventDesc())
                .eventObs(spaceRndR_Request.getEventObs())
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING))
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

    /**
     * Converts SpaceRequest and SpaceReservation entities into a SpaceRndRResponse DTO.
     * <p>
     * This method prepares the response data, converting entities into their respective DTOs for returning to the client.
     *
     * @param sReq The {@link SpaceRequest} entity.
     * @param sRes The {@link SpaceReservation} entity.
     * @return The corresponding {@link SpaceRndRResponse} DTO.
     */
    public SpaceRndRResponse toDTO(SpaceRequest sReq, SpaceReservation sRes) {
        return SpaceRndRResponse.builder()
                .id(sReq.getId())
                .space(spaceMapper.toDTO(sReq.getSpace()))
                .reqAndResId(sReq.getId())
                .numPeople(sReq.getNumPeople())
                .eventDesc(sReq.getEventDesc())
                .eventObs(sReq.getEventObs())
                .status(resourceRequestStatusMapper.toDTO(sReq.getStatus()))
                .useEquipment(sReq.getUseEquipment())
                .startTime(sRes != null ? sRes.getStartTime() : null)
                .endTime(sRes != null ? sRes.getEndTime() : null)
                .user(myUserMapper.toDTO(sReq.getUser()))
                .createdAt(sReq.getCreatedAt().toString())
                .build();
    }

}
