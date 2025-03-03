package com.impact.core.module.productRequest.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productRequest.mapper.ProductRequestMapper;
import com.impact.core.module.productRequest.payload.request.ProductRequestDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductRequestDTOResponse;
import com.impact.core.module.productRequest.repository.ProductRequestRepository;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("productRequestService")
@RequiredArgsConstructor
public class ProductRequestService {
    public final ProductRequestRepository productRequestRepository;
    public final ProductRequestMapper productRequestMapper;
    public final UserService userService;
    public final MailService mailService;

    public ProductRequestDTOResponse save(UserDetailsImpl userDetails, ProductRequestDTORequest productRequestDTORequest) {
        ProductRequest productRequest = productRequestMapper.toEntity(productRequestDTORequest);
        User user = userService.findById(userDetails.getId());
        productRequest.setUser(user);
        ProductRequest productRequestSaved = productRequestRepository.save(productRequest);

        ComposedMail composedMailToUser = MailFactory.createProductRequestEmail(productRequestSaved);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.createAdminReviewProductRequest(productRequestSaved);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        return productRequestMapper.toDTO(productRequestSaved);
    }

    public ProductRequestDTOResponse update(int id, ProductRequestDTORequest productRequestDTORequest) {
        ProductRequest productRequest = this.findById(id);
        ProductRequest productRequestUpdated = productRequestMapper.toEntity(productRequestDTORequest);
        productRequestUpdated.setId(productRequest.getId());
        productRequestUpdated.setUser(productRequest.getUser());
        productRequestUpdated.setCreatedAt(productRequest.getCreatedAt());
        ProductRequest productRequestSaved = productRequestRepository.save(productRequestUpdated);
        return productRequestMapper.toDTO(productRequestSaved);
    }

    public ProductRequestDTOResponse delete(int id) {
        ProductRequest productRequest = findById(id);
        productRequestRepository.delete(productRequest);
        return productRequestMapper.toDTO(productRequest);
    }

    public ProductRequest findById(int id) {
        return productRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La solicitud de producto con el id: " + id + " no existe en la base de datos."));
    }

    public List<ProductRequestDTOResponse> findAll() {
        return productRequestRepository.findAll().stream()
                .map(productRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<ProductRequestDTOResponse> findByUser(int id){
        return productRequestRepository.productsRequestByUser(id).stream()
                .map(productRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

}
