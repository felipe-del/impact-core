package com.impact.core.module.resource_request_status.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.mapper.ResourceRequestStatusMapper;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.resource_request_status.repository.ResourceRequestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class responsible for managing the resource request statuses.
 * <p>
 * This service provides functionality for retrieving resource request statuses
 * by name and fetching all available statuses.
 */
@Service("resourceRequestStatusService")
@RequiredArgsConstructor
public class ResourceRequestStatusService {
    public final ResourceRequestStatusRepository resourceRequestStatusRepository;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;

    /**
     * Retrieves a resource request status by its name.
     * <p>
     * If the resource request status is not found in the database, a custom exception
     * (`ResourceNotFoundException`) will be thrown.
     *
     * @param name the name of the resource request status
     * @return the {@link ResourceRequestStatus} object corresponding to the name
     * @throws ResourceNotFoundException if the resource request status is not found
     */
    public ResourceRequestStatus findByName(EResourceRequestStatus name) {
        return resourceRequestStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("El estado de solicitud de recurso : " + name + " no se encuentra en la base de datos"));
    }

    /**
     * Retrieves a list of all resource request statuses.
     * <p>
     * The method maps the entity objects to their corresponding DTOs before returning the list.
     *
     * @return a list of {@link ResourceRequestStatusResponse} objects
     */
    public List<ResourceRequestStatusResponse> findAll() {
        return resourceRequestStatusRepository.findAll().stream()
                .map(resourceRequestStatusMapper::toDTO)
                .toList();
    }
}
