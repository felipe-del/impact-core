package com.impact.core.module.spaceRequest_Reservation.service;

import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceReservation;
import com.impact.core.module.spaceRequest_Reservation.mapper.SpaceRndRMapper;
import com.impact.core.module.spaceRequest_Reservation.payload.request.SpaceRndRRequest;
import com.impact.core.module.spaceRequest_Reservation.payload.response.SpaceRndRResponse;
import com.impact.core.module.spaceRequest_Reservation.repository.SpaceRequestRepository;
import com.impact.core.module.spaceRequest_Reservation.repository.SpaceReservationRepository;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("spaceRndRService")
@RequiredArgsConstructor
public class SpaceRndRService {

    public final SpaceRequestRepository spaceRequestRepository;
    public final SpaceReservationRepository spaceReservationRepository;
    public final SpaceRndRMapper spaceRndRMapper;

    public final UserService userService;
    public final MailService mailService;

    public SpaceRndRResponse save(UserDetailsImpl userDetails, SpaceRndRRequest spaceRndRRequest) {
        // Because of how pair works, (a = SpaceRequest, b = SpaceReservation)
        Pair<SpaceRequest, SpaceReservation> requestAndReservation = spaceRndRMapper.toEntity(spaceRndRRequest);
        User user = userService.findById(userDetails.getId());

        // Setting the user since it is only attribute not set by the SpaceRndRMapper
        requestAndReservation.a.setUser(user); // SpaceRequest.setUser
        requestAndReservation.b.setUser(user); // SpaceReservation.setUser

        // We now save the modified SpaceRequest and SpaceReservation with their respective repos
        SpaceRequest spaceRequestSaved = spaceRequestRepository.save(requestAndReservation.a);
        SpaceReservation spaceReservationSaved = spaceReservationRepository.save(requestAndReservation.b);

        // SENDING MAILS IS PENDING
        // FELIPE LO TIENE QUE HACER PORQUE SE OFRECIO EL 03/03/2025

        return spaceRndRMapper.toDTO(spaceRequestSaved, spaceReservationSaved);
    }


    public List<SpaceRndRResponse> getAll() {
        List<SpaceRequest>  requests = spaceRequestRepository.findAll();
        List<SpaceReservation> reservations = spaceReservationRepository.findAll();

        List<SpaceRndRResponse> responses = new ArrayList<>();

        Iterator<SpaceRequest> reqIterator = requests.iterator();
        Iterator<SpaceReservation> resIterator = reservations.iterator();

        while(reqIterator.hasNext() && resIterator.hasNext()) {
            SpaceRequest req = reqIterator.next();
            SpaceReservation res = resIterator.next();

            responses.add(spaceRndRMapper.toDTO(req, res));
        }

        return responses;
    }
}
