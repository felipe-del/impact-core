package com.impact.core.module.productStatus.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.productStatus.entity.ProductStatus;
import com.impact.core.module.productStatus.enun.EProductStatus;
import com.impact.core.module.productStatus.mapper.ProductStatusMapper;
import com.impact.core.module.productStatus.payload.response.ProductStatusResponse;
import com.impact.core.module.productStatus.repository.ProductStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("productStatusService")
@RequiredArgsConstructor
public class ProductStatusService {
    public final ProductStatusRepository productStatusRepository;
    public final ProductStatusMapper productStatusMapper;

    public ProductStatus findByName(EProductStatus name) {
        return productStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("El estado: " + name + " no existe en la base de datos."));
    }

    public List<ProductStatusResponse> findAll() {
        return productStatusRepository.findAll().stream()
                .map(productStatusMapper::toDTO)
                .collect(Collectors.toList());
    }
}
