package com.impact.core.module.assetRequest.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.assetRequest.entity.AssetPetition;
import com.impact.core.module.assetRequest.mapper.AssetPetitionMapper;
import com.impact.core.module.assetRequest.payload.request.AssetPetitionDTORequest;
import com.impact.core.module.assetRequest.payload.response.AssetPetitionDTOResponse;
import com.impact.core.module.assetRequest.repository.AssetPetitionRepository;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Service("assetPetitionService")
@RequiredArgsConstructor
public class AssetPetitionService {
    public final AssetPetitionRepository assetPetitionRepository;
    public final AssetPetitionMapper assetPetitionMapper;
    public final UserService userService;
    public final MailService mailService;


    public AssetPetitionDTOResponse save(UserDetailsImpl userDetails, AssetPetitionDTORequest assetPetitionDTORequest) {
        AssetPetition assetPetition = assetPetitionMapper.toEntity(assetPetitionDTORequest);
        User user = userService.findById(userDetails.getId());
        assetPetition.setUser(user);
        AssetPetition assetPetitionSaved = assetPetitionRepository.save(assetPetition);

        ComposedMail composedMailToUser = MailFactory.createAssetPetitionEmail(assetPetitionSaved);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.createAdminReviewAssetPetition(assetPetitionSaved);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        return assetPetitionMapper.toDTO(assetPetitionSaved);
    }

    public AssetPetitionDTOResponse update(int id, AssetPetitionDTORequest assetPetitionDTORequest) {
        AssetPetition assetPetition = this.findById(id);
        AssetPetition assetPetitionUpdated = assetPetitionMapper.toEntity(assetPetitionDTORequest);
        assetPetitionUpdated.setId(assetPetition.getId());
        assetPetitionUpdated.setUser(assetPetition.getUser());
        assetPetitionUpdated.setCreatedAt(assetPetition.getCreatedAt());
        AssetPetition assetPetitionSaved = assetPetitionRepository.save(assetPetitionUpdated);
        return assetPetitionMapper.toDTO(assetPetitionSaved);
    }

    public void delete(int id) {
        AssetPetition assetPetition = findById(id);
        assetPetitionRepository.delete(assetPetition);
    }

    public AssetPetition findById(int id) {
        return assetPetitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud con id: " + id + " no encontrada."));
    }

    public List<AssetPetitionDTOResponse> findAll() {
        return assetPetitionRepository.findAll().stream()
                .map(assetPetitionMapper::toDTO)
                .collect(Collectors.toList());
    }

}
