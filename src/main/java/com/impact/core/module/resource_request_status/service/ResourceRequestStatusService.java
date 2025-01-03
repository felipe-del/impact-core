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

@Service("resourceRequestStatusService")
@RequiredArgsConstructor
public class ResourceRequestStatusService {
    public final ResourceRequestStatusRepository resourceRequestStatusRepository;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;

    public ResourceRequestStatus findByName(EResourceRequestStatus name) {
        return resourceRequestStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("El estado de solicitud de recurso : " + name + " no se encuentra en la base de datos"));
    }

    public List<ResourceRequestStatusResponse> findAll() {
        return resourceRequestStatusRepository.findAll().stream()
                .map(resourceRequestStatusMapper::toDTO)
                .toList();
    }
}
