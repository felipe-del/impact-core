package com.impact.core.module.productRequest.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.productsOfRequest.Service.ProductsOfRequestServive;
import com.impact.core.module.productsOfRequest.entity.ProductsOfRequest;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.repository.ProductRepository;
import com.impact.core.module.product.service.ProductService;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productRequest.mapper.ProductRequestMapper;
import com.impact.core.module.productRequest.payload.request.ProductRequestDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductRequestDTOResponse;
import com.impact.core.module.productRequest.repository.ProductRequestRepository;
import com.impact.core.module.productStatus.enun.EProductStatus;
import com.impact.core.module.productStatus.service.ProductStatusService;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("productRequestService")
@RequiredArgsConstructor
public class ProductRequestService {
    public final ProductRequestRepository productRequestRepository;
    public final ProductService productService;
    public final ProductsOfRequestServive productsOfRequestServive;
    public final ProductRepository productRepository;
    public final ProductStatusService productStatusService;
    public final ProductRequestMapper productRequestMapper;
    public final UserService userService;
    public final MailService mailService;

    public ProductRequestDTOResponse save(UserDetailsImpl userDetails, ProductRequestDTORequest productRequestDTORequest) {
        ProductRequest productRequest = productRequestMapper.toEntity(productRequestDTORequest);
        User user = userService.findById(userDetails.getId());
        productRequest.setUser(user);



        Product productToRequest = productService.findProductById(productRequestDTORequest.getProductId());
        int requestedQuantity = productRequestDTORequest.getQuantity();
        List<Product> availableProducts =
                productService.getAvailableProductsByCategory(productToRequest.getCategory().getName(), requestedQuantity);

        if (availableProducts.size() < requestedQuantity) {
            throw new ResourceNotFoundException("No hay suficientes productos disponibles en esta categoría.");
        }

        ProductRequest productRequestSaved = productRequestRepository.save(productRequest);

        availableProducts.stream().limit(requestedQuantity).forEach(product -> {
            product.setStatus(productStatusService.findByName(EProductStatus.PRODUCT_STATUS_EARRING));
            productRepository.save(product);

            // Crear relación en `products_of_request`
            ProductsOfRequest productsOfRequest = new ProductsOfRequest();
            productsOfRequest.setProduct(product);
            productsOfRequest.setProductRequest(productRequestSaved);
            productsOfRequestServive.save(productsOfRequest);
        });


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
    @Transactional
    public void cancelRequest(Integer status, Integer productRequestId, Integer productStatus, String cancelReason){
        ProductRequest productRequest = findById(productRequestId);
        ComposedMail composedMailToUser = MailFactory.composeUserNotificationCancelProductRequest(productRequest, cancelReason);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.composeAdminNotificationCancelProductRequest(productRequest, cancelReason);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        productRequestRepository.updateProductRequestStatus(status,productRequestId);
        productsOfRequestServive.cancelRequest(productRequestId,productStatus);
    }
    @Transactional
    public void acceptRequest(Integer status, Integer productRequestId){
        productRequestRepository.updateProductRequestStatus(status,productRequestId);
    }

    public List<ProductRequest> findByPending(){
        return productRequestRepository.productsRequestByStatus(1); //status 1-> RESOURCE_REQUEST_STATUS_EARRING
    }

    public List<ProductRequestDTOResponse> findAllExcludingEarringAndRenewal() {
        List<ProductRequest> allRequests = productRequestRepository.findAll();

        return allRequests.stream()
                .filter(request -> {
                    EResourceRequestStatus statusEnum = request.getStatus().getName();
                    return statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING &&
                            statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_RENEWAL;
                })
                .map(productRequestMapper::toDTO)
                .toList();
    }
}
