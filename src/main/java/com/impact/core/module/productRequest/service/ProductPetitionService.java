package com.impact.core.module.productRequest.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.productRequest.entity.ProductPetition;
import com.impact.core.module.productRequest.mapper.ProductPetitionMapper;
import com.impact.core.module.productRequest.payload.request.ProductPetitionDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductPetitionDTOResponse;
import com.impact.core.module.productRequest.repository.ProductPetitionRepository;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("productRequestService")
@RequiredArgsConstructor
public class ProductPetitionService {
    public final ProductPetitionRepository productPetitionRepository;
    public final ProductPetitionMapper productPetitionMapper;
    public final UserService userService;
    public final MailService mailService;

    public ProductPetitionDTOResponse save(UserDetailsImpl userDetails, ProductPetitionDTORequest productPetitionDTORequest) {
        ProductPetition productPetition = productPetitionMapper.toEntity(productPetitionDTORequest);
        User user = userService.findById(userDetails.getId());
        productPetition.setUser(user);
        ProductPetition productPetitionSaved = productPetitionRepository.save(productPetition);

        ComposedMail composedMail = MailFactory.createProductRequestEmail(productPetitionSaved);
        mailService.sendComposedEmail(composedMail);
        ComposedMail composedMailToAdmin = MailFactory.createAdminReviewRequest(productPetitionSaved);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        return productPetitionMapper.toDTO(productPetitionSaved);
    }

    public ProductPetitionDTOResponse update(int id, ProductPetitionDTORequest productPetitionDTORequest) {
        ProductPetition productPetition = findProductRequestById(id);
        ProductPetition productPetitionUpdated = productPetitionMapper.toEntity(productPetitionDTORequest);
        productPetitionUpdated.setId(productPetition.getId());
        productPetitionUpdated.setUser(productPetition.getUser());
        ProductPetition productPetitionSaved = productPetitionRepository.save(productPetitionUpdated);
        return productPetitionMapper.toDTO(productPetitionSaved);
    }

    public ProductPetitionDTOResponse delete(int id) {
        ProductPetition productPetition = findProductRequestById(id);
        productPetitionRepository.delete(productPetition);
        return productPetitionMapper.toDTO(productPetition);
    }

    public ProductPetition findProductRequestById(int id) {
        return productPetitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La solicitud de producto con el id: " + id + " no existe en la base de datos."));
    }

    public List<ProductPetitionDTOResponse> findAll() {
        return productPetitionRepository.findAll().stream()
                .map(productPetitionMapper::toDTO)
                .collect(Collectors.toList());
    }

}
