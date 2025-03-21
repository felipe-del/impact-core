package com.impact.core.module.request.mapper;


import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.request.payload.RequestDTOResponse;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceReservation;
import com.impact.core.module.spaceRequest_Reservation.repository.SpaceReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    public final SpaceReservationRepository spaceReservationRepository;
    public RequestDTOResponse toDTO(AssetRequest request) {
        return  RequestDTOResponse.builder()
                .id(request.getId())
                .user(request.getUser().getName())
                .requestDate(request.getCreatedAt())
                .dueDate(request.getExpirationDate())
                .requestedItem(request.getAsset().getSubcategory().getDescription())
                .type("Activo")
                .detail(request.getReason())
                .status(request.getStatus().getDescription())
                .build();
    }

    public RequestDTOResponse toDTO(ProductRequest request) {
        return  RequestDTOResponse.builder()
                .id(request.getId())
                .user(request.getUser().getName())
                .requestDate(request.getCreatedAt())
                .dueDate(null)
                .requestedItem(request.getProduct().getCategory().getName())
                .type("Producto")
                .detail(request.getReason())
                .status(request.getStatus().getDescription())
                .build();
    }

    public RequestDTOResponse toDTO(SpaceRequest request) {
        Optional<SpaceReservation> reservation= spaceReservationRepository.findById(request.getId());
        return  RequestDTOResponse.builder()
                .id(request.getId())
                .user(request.getUser().getName())
                .startTime(reservation.map(SpaceReservation::getStartTime).orElse(null))
                .endTime(reservation.map(SpaceReservation::getEndTime).orElse(null))
                .requestedItem(request.getSpace().getName())
                .type("Espacio")
                .detail(request.getEventDesc())
                .status(request.getStatus().getDescription())
                .build();
    }

    public List<RequestDTOResponse> toDTOList(List<AssetRequest> assetRequests, List<ProductRequest> productRequests, List<SpaceRequest> spaceRequests) {
        List<RequestDTOResponse> assetDTOs = assetRequests.stream().map(this::toDTO).collect(Collectors.toList());
        List<RequestDTOResponse> productDTOs = productRequests.stream().map(this::toDTO).toList();
        List<RequestDTOResponse> spaceDTOs = spaceRequests.stream().map(this::toDTO).toList();

        assetDTOs.addAll(productDTOs);
        assetDTOs.addAll(spaceDTOs);
        return assetDTOs;
    }
}
