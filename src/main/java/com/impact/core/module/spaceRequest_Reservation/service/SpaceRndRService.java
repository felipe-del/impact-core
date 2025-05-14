package com.impact.core.module.spaceRequest_Reservation.service;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
import com.impact.core.module.space.entity.Space;
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
import java.util.stream.Collectors;

/**
 * Service class responsible for managing space requests and reservations.
 * Handles creating, updating, and canceling space requests and reservations, as well as sending notifications.
 */
@Service("spaceRndRService")
@RequiredArgsConstructor
public class SpaceRndRService {

    public final SpaceRequestRepository spaceRequestRepository;
    public final SpaceReservationRepository spaceReservationRepository;
    public final SpaceRndRMapper spaceRndRMapper;
    public final UserService userService;
    public final MailService mailService;
    private final SpaceStatusService spaceStatusService;
    private final ResourceRequestStatusService resourceRequestStatusService;

    /**
     * Saves a new space request and reservation, including validation, conflict checking,
     * and sending confirmation emails.
     *
     * @param userDetails     The current user details.
     * @param spaceRndRRequest The request data for the space and reservation.
     * @return The saved {@link SpaceRndRResponse} object with the space request and reservation details.
     * @throws ConflictException if there is a conflict with the request (e.g., the requested time is outside operating hours or conflicts with existing reservations).
     */
    public SpaceRndRResponse save(UserDetailsImpl userDetails, SpaceRndRRequest spaceRndRRequest) {
        // Pairing the space request and reservation entities
        Pair<SpaceRequest, SpaceReservation> requestAndReservation = spaceRndRMapper.toEntity(spaceRndRRequest);
        User user = userService.findById(userDetails.getId());
        Space space = requestAndReservation.a.getSpace();
        LocalTime openTime = space.getOpenTime();
        LocalTime closeTime = space.getCloseTime();

        Instant startTime = requestAndReservation.b.getStartTime();
        Instant endTime = requestAndReservation.b.getEndTime();

        // Convert Instant to LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.ofInstant(startTime, ZoneOffset.UTC);
        LocalDateTime endDateTime = LocalDateTime.ofInstant(endTime, ZoneOffset.UTC);

        // Check if the reservation is within the space's allowed hours
        LocalTime startLocalTime = startDateTime.toLocalTime();
        LocalTime endLocalTime = endDateTime.toLocalTime();

        if (startLocalTime.isBefore(openTime) || endLocalTime.isAfter(closeTime)) {
            throw new ConflictException(String.format(
                    "El espacio '%s' solo está disponible de %s a %s.",
                    space.getName(), openTime, closeTime
            ));
        }

        // Check for conflicting reservations
        List<SpaceReservation> reservations = spaceReservationRepository.findAllBySpace(space);
        for (SpaceReservation reservation : reservations) {
            LocalDateTime reservedStartDateTime = LocalDateTime.ofInstant(reservation.getStartTime(), ZoneOffset.UTC);
            LocalDateTime reservedEndDateTime = LocalDateTime.ofInstant(reservation.getEndTime(), ZoneOffset.UTC);

            if (reservedStartDateTime.isBefore(endDateTime) && reservedEndDateTime.isAfter(startDateTime)) {
                throw new ConflictException(String.format(
                        "El espacio '%s' ya está reservado en ese horario. Próxima disponibilidad después de %s.",
                        space.getName(), reservedEndDateTime
                ));
            }
        }

        // Set user for space request and reservation
        requestAndReservation.a.setUser(user);
        requestAndReservation.b.setUser(user);

        SpaceRequest spaceRequestSaved = spaceRequestRepository.save(requestAndReservation.a);
        SpaceReservation spaceReservationSaved = spaceReservationRepository.save(requestAndReservation.b);

        ComposedMail composedMailToUser = MailFactory.createSpaceRequestEmail(spaceRequestSaved, spaceReservationSaved);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.createAdminReviewSpaceRequest(spaceRequestSaved, spaceReservationSaved);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        return spaceRndRMapper.toDTO(spaceRequestSaved, spaceReservationSaved);
    }

    /**
     * Retrieves all space requests and reservations and returns them as a list of SpaceRndRResponse objects.
     *
     * @return A list of {@link SpaceRndRResponse} objects representing all space requests and reservations.
     */
    public List<SpaceRndRResponse> getAll() {
        List<SpaceRequest>  requests = spaceRequestRepository.findAll();
        List<SpaceReservation> reservations = spaceReservationRepository.findAll();

        return joinSpaceRandResponse(requests,reservations);
    }

    /**
     * Joins the space requests and reservations and converts them to {@link SpaceRndRResponse} objects.
     *
     * @param requests     A list of space requests.
     * @param reservations A list of space reservations.
     * @return A list of {@link SpaceRndRResponse} objects representing the joined data.
     */
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

    /**
     * Retrieves space requests and reservations for a specific user.
     *
     * @param id The user ID.
     * @return A list of {@link SpaceRndRResponse} objects representing the user's space requests and reservations.
     */
    public List<SpaceRndRResponse> getByUser(Integer id){
        List<SpaceRequest>  requests = spaceRequestRepository.spaceRequestByUser(id);
        List<SpaceReservation> reservations = spaceReservationRepository.spaceReservationByUser(id);

        return joinSpaceRandResponse(requests,reservations);
    }

    /**
     * Cancels a space reservation by its ID.
     *
     * @param resId The reservation ID.
     */
    public void cancelReservation(Integer resId){
        Optional<SpaceReservation> sr= spaceReservationRepository.findById(resId);
        sr.ifPresent(spaceReservationRepository::delete);
    }

    /**
     * Finds a space request by its ID.
     *
     * @param id The space request ID.
     * @return The {@link SpaceRequest} object.
     * @throws ResourceNotFoundException If the space request is not found.
     */
    public SpaceRequest findById(int id) {
        return spaceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La solicitud de espacio con el id: " + id + " no existe en la base de datos."));
    }

    /**
     * Cancels a space request and reservation by changing the status and sending email notifications.
     *
     * @param status      The new status for the space request.
     * @param reqId       The space request ID.
     * @param cancelReason The reason for cancellation.
     */
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

    /**
     * Accepts a space request by changing the status and sending email notifications.
     *
     * @param status  The new status for the space request.
     * @param reqId   The space request ID.
     */
    @Transactional
    public void acceptRequest(Integer status,Integer reqId){
        spaceRequestRepository.updateSpaceRequest(status,reqId);
    }

    /**
     * Finds all pending space requests.
     *
     * @return A list of pending space requests.
     */
    public List<SpaceRequest> findByPending(){
        return spaceRequestRepository.spaceRequestByStatus(1); //status 1-> RESOURCE_REQUEST_STATUS_EARRING
    }

    /**
     * Finds all space requests excluding those with "Earring" and "Renewal" status.
     *
     * @return A list of space requests excluding "Earring" and "Renewal" statuses.
     */
    public List<SpaceRndRResponse> findAllExcludingEarringAndRenewal() {
        List<SpaceRequest> allRequests = spaceRequestRepository.findAll();

        return allRequests.stream()
                .filter(request -> {
                    EResourceRequestStatus statusEnum = request.getStatus().getName();
                    return statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING &&
                            statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_RENEWAL;
                })
                .map(request -> spaceRndRMapper.toDTO(request, null))
                .toList();
    }

    /**
     * Finds all space requests with the "Earring" status.
     *
     * @return A list of space requests with the "Earring" status.
     */
    public List<SpaceRndRResponse> findAllWithEarring() {
        List<SpaceRequest> allRequests = spaceRequestRepository.findAll();

        return allRequests.stream()
                .filter(request -> request.getStatus().getName() == EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING)
                .map(request -> spaceRndRMapper.toDTO(request, null))
                .collect(Collectors.toList());
    }

    /**
     * Accepts a space request by changing its status to accepted and updating the space status.
     * Sends email notifications to the user and admins.
     *
     * @param spaceRequestId The space request ID.
     * @return The {@link SpaceRndRResponse} object with updated space request and reservation details.
     * @throws ConflictException if the space request is not in "Earring" status.
     */
    public SpaceRndRResponse acceptRequest(Integer spaceRequestId) {
        SpaceRequest spaceRequest = findById(spaceRequestId);
        List<SpaceReservation> spaceReservations = spaceReservationRepository.findAllBySpace(spaceRequest.getSpace());
        if(spaceRequest.getStatus().getName() != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING){
            throw new ConflictException("La solicitud de espacio con el id: " + spaceRequestId + " no está en espera.");
        }
        spaceRequest.setStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_ACCEPTED)
        );
        Space space = spaceRequest.getSpace();
        space.setStatus(
                spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_LOANED)
        );
        SpaceRequest saved = spaceRequestRepository.save(spaceRequest);

        ComposedMail composedMailToUser = MailFactory.createSpaceRequestAcceptEmail(spaceRequest);
        mailService.sendComposedEmail(composedMailToUser);

        return spaceRndRMapper.toDTO(saved, spaceReservations.getFirst());
    }

    /**
     * Rejects a space request by changing its status to canceled and updating the space status.
     * Sends email notifications to the user and admins.
     *
     * @param spaceRequestId The space request ID.
     * @return The {@link SpaceRndRResponse} object with updated space request and reservation details.
     * @throws ConflictException if the space request has already been accepted or canceled.
     */
    public SpaceRndRResponse rejectRequest(Integer spaceRequestId) {
        SpaceRequest spaceRequest = findById(spaceRequestId);
        List<SpaceReservation> spaceReservations = spaceReservationRepository.findAllBySpace(spaceRequest.getSpace());
        if(spaceRequest.getStatus().getName() == EResourceRequestStatus.RESOURCE_REQUEST_STATUS_ACCEPTED){
            throw new ConflictException("La solicitud de espacio con el id: " + spaceRequestId + " ya fué aceptada.");
        }
        if(spaceRequest.getStatus().getName() == EResourceRequestStatus.RESOURCE_REQUEST_STATUS_CANCELED){
            throw new ConflictException("La solicitud de espacio con el id: " + spaceRequestId + " ya fué cancelado.");
        }
        spaceRequest.setStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_CANCELED)
        );
        Space space = spaceRequest.getSpace();
        space.setStatus(
                spaceStatusService.findByName(ESpaceStatus.SPACE_STATUS_AVAILABLE)
        );
        SpaceRequest saved = spaceRequestRepository.save(spaceRequest);

        ComposedMail composedMailToUser = MailFactory.createSpaceRequestRejectEmail(spaceRequest);
        mailService.sendComposedEmail(composedMailToUser);

        return spaceRndRMapper.toDTO(saved, spaceReservations.getFirst());
    }

}
