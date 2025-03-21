package com.impact.core.module.spaceRequest_Reservation.service;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.space.entity.Space;
import com.impact.core.module.space.respository.SpaceRepository;
import com.impact.core.module.space.service.SpaceService;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceReservation;
import com.impact.core.module.spaceRequest_Reservation.mapper.SpaceRndRMapper;
import com.impact.core.module.spaceRequest_Reservation.payload.request.SpaceRndRRequest;
import com.impact.core.module.spaceRequest_Reservation.payload.response.SpaceRndRResponse;
import com.impact.core.module.spaceRequest_Reservation.repository.SpaceRequestRepository;
import com.impact.core.module.spaceRequest_Reservation.repository.SpaceReservationRepository;
import com.impact.core.module.spaceStatus.enun.ESpaceStatus;
import com.impact.core.module.spaceStatus.service.SpaceStatusService;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service("spaceRndRService")
@RequiredArgsConstructor
public class SpaceRndRService {

    public final SpaceRequestRepository spaceRequestRepository;
    public final SpaceReservationRepository spaceReservationRepository;
    public final SpaceRndRMapper spaceRndRMapper;
    public final UserService userService;
    public final MailService mailService;
    private final SpaceStatusService spaceStatusService;
    private final SpaceRepository spaceRepository;

    public SpaceRndRResponse save(UserDetailsImpl userDetails, SpaceRndRRequest spaceRndRRequest) {
        // Because of how pair works, (a = SpaceRequest, b = SpaceReservation)
        Pair<SpaceRequest, SpaceReservation> requestAndReservation = spaceRndRMapper.toEntity(spaceRndRRequest);
        User user = userService.findById(userDetails.getId());
        Space space = requestAndReservation.a.getSpace();
        LocalTime openTime = space.getOpenTime();
        LocalTime closeTime = space.getCloseTime();
        Instant startTime = requestAndReservation.b.getStartTime();
        Instant endTime = requestAndReservation.b.getEndTime();

        // Convertir los Instant a LocalTime directamente, sin usar ZoneId
        LocalTime startLocalTime = LocalTime.ofInstant(startTime, ZoneOffset.UTC);  // Suponiendo que los Instant están en UTC
        LocalTime endLocalTime = LocalTime.ofInstant(endTime, ZoneOffset.UTC);  // Suponiendo que los Instant están en UTC

        // Validar si la reserva está dentro del horario permitido
        if (startLocalTime.isBefore(openTime) || endLocalTime.isAfter(closeTime)) {
            throw new ConflictException(String.format(
                    "El espacio '%s' solo está disponible de %s a %s.",
                    space.getName(), openTime, closeTime
            ));
        }

        // Obtener reservas activas en el mismo espacio
        List<SpaceReservation> reservations = spaceReservationRepository.findAllBySpace(space);

        for (SpaceReservation reservation : reservations) {
            Instant reservedStart = reservation.getStartTime();
            Instant reservedEnd = reservation.getEndTime();

            // Convertir los Instant de las reservas existentes a LocalTime
            LocalTime reservedStartLocal = LocalTime.ofInstant(reservedStart, ZoneOffset.UTC);
            LocalTime reservedEndLocal = LocalTime.ofInstant(reservedEnd, ZoneOffset.UTC);

            // Verificar si hay solapamiento con otra reserva
            if (reservedStartLocal.isBefore(endLocalTime) && reservedEndLocal.isAfter(startLocalTime)) {
                throw new ConflictException(String.format(
                        "El espacio '%s' ya está reservado en ese horario. Próxima disponibilidad después de %s.",
                        space.getName(), reservedEndLocal
                ));
            }
        }

        //space.setStatus(spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_EARRING));
        //spaceRepository.save(space); // Update space status

        // Setting the user since it is only attribute not set by the SpaceRndRMapper
        requestAndReservation.a.setUser(user); // SpaceRequest.setUser
        requestAndReservation.b.setUser(user); // SpaceReservation.setUser

        // We now save the modified SpaceRequest and SpaceReservation with their respective repos
        SpaceRequest spaceRequestSaved = spaceRequestRepository.save(requestAndReservation.a);
        SpaceReservation spaceReservationSaved = spaceReservationRepository.save(requestAndReservation.b);

        ComposedMail composedMailToUser = MailFactory.createSpaceRequestEmail(spaceRequestSaved, spaceReservationSaved);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.createAdminReviewSpaceRequest(spaceRequestSaved, spaceReservationSaved);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        return spaceRndRMapper.toDTO(spaceRequestSaved, spaceReservationSaved);
    }


    public List<SpaceRndRResponse> getAll() {
        List<SpaceRequest>  requests = spaceRequestRepository.findAll();
        List<SpaceReservation> reservations = spaceReservationRepository.findAll();

        return joinSpaceRandResponse(requests,reservations);
    }
    public List<SpaceRndRResponse> joinSpaceRandResponse(List<SpaceRequest> requests, List<SpaceReservation> reservations){
        List<SpaceRndRResponse> responses = new ArrayList<>();

        Iterator<SpaceRequest> reqIterator = requests.iterator();
        Iterator<SpaceReservation> resIterator = reservations.iterator();

        while (reqIterator.hasNext()) {
            SpaceRequest req = reqIterator.next();
            SpaceReservation res = (resIterator.hasNext()) ? resIterator.next() : null;

            responses.add(spaceRndRMapper.toDTO(req, res));
        }

        return responses;
    }

    public List<SpaceRndRResponse> getByUser(Integer id){
        List<SpaceRequest>  requests = spaceRequestRepository.spaceRequestByUser(id);
        List<SpaceReservation> reservations = spaceReservationRepository.spaceReservationByUser(id);

        return joinSpaceRandResponse(requests,reservations);
    }

    public void cancelReservation(Integer resId){
        Optional<SpaceReservation> sr= spaceReservationRepository.findById(resId);
        sr.ifPresent(spaceReservationRepository::delete);
    }

    public SpaceRequest findById(int id) {
        return spaceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La solicitud de espacio con el id: " + id + " no existe en la base de datos."));
    }

    @Transactional
    public void cancelRequest(Integer status,Integer reqId, String cancelReason){
        SpaceRequest spaceRequest = findById(reqId);
        ComposedMail composedMailToUser = MailFactory.composeUserNotificationCancelSpaceRequest(spaceRequest, cancelReason);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.composeAdminNotificationCancelSpaceRequest(spaceRequest, cancelReason);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        spaceRequestRepository.updateSpaceRequest(status,reqId);
        cancelReservation(reqId);
    }

    @Transactional
    public void acceptRequest(Integer status,Integer reqId){

        spaceRequestRepository.updateSpaceRequest(status,reqId);
    }

    public List<SpaceRequest> findByPending(){
        return spaceRequestRepository.spaceRequestByStatus(1); //status 1-> RESOURCE_REQUEST_STATUS_EARRING
    }
}
