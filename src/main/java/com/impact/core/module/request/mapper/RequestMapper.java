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

/**
 * Mapper class responsible for converting different types of requests (Asset, Product, and Space)
 * into a unified {@link RequestDTOResponse} format for response purposes.
 * <p>
 * This class contains methods to transform entities like {@link AssetRequest}, {@link ProductRequest},
 * and {@link SpaceRequest} into their corresponding DTO representations to be used in the API responses.
 */
@Component
@RequiredArgsConstructor
public class RequestMapper {

    public final SpaceReservationRepository spaceReservationRepository;

    /**
     * Converts an {@link AssetRequest} entity into a {@link RequestDTOResponse} for API responses.
     *
     * @param request The {@link AssetRequest} entity to be converted.
     * @return A {@link RequestDTOResponse} containing the data of the asset request.
     */
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

    /**
     * Converts a {@link ProductRequest} entity into a {@link RequestDTOResponse} for API responses.
     *
     * @param request The {@link ProductRequest} entity to be converted.
     * @return A {@link RequestDTOResponse} containing the data of the product request.
     */
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

    /**
     * Converts a {@link SpaceRequest} entity into a {@link RequestDTOResponse} for API responses.
     * Fetches corresponding reservation details if available.
     *
     * @param request The {@link SpaceRequest} entity to be converted.
     * @return A {@link RequestDTOResponse} containing the data of the space request.
     */
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

    /**
     * Converts lists of {@link AssetRequest}, {@link ProductRequest}, and {@link SpaceRequest} entities into a unified list
     * of {@link RequestDTOResponse} for API responses.
     *
     * @param assetRequests A list of {@link AssetRequest} entities to be converted.
     * @param productRequests A list of {@link ProductRequest} entities to be converted.
     * @param spaceRequests A list of {@link SpaceRequest} entities to be converted.
     * @return A unified list of {@link RequestDTOResponse} containing the data of all requests.
     */
    public List<RequestDTOResponse> toDTOList(List<AssetRequest> assetRequests, List<ProductRequest> productRequests, List<SpaceRequest> spaceRequests) {
        List<RequestDTOResponse> assetDTOs = assetRequests.stream().map(this::toDTO).collect(Collectors.toList());
        List<RequestDTOResponse> productDTOs = productRequests.stream().map(this::toDTO).toList();
        List<RequestDTOResponse> spaceDTOs = spaceRequests.stream().map(this::toDTO).toList();

        assetDTOs.addAll(productDTOs);
        assetDTOs.addAll(spaceDTOs);
        return assetDTOs;
    }
}
