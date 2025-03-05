package com.impact.core.module.ProductsOfRequest.Service;

import com.impact.core.module.ProductsOfRequest.entity.ProductsOfRequest;
import com.impact.core.module.ProductsOfRequest.repository.ProductsOfRequestRepository;
import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.repository.ProductRepository;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service("productsOfRequestService")
@RequiredArgsConstructor
public class ProductsOfRequestServive {

    @Autowired
    ProductsOfRequestRepository productsOfRequestRepository;
    @Autowired
    ProductRepository productRepository;

    public List<Product> getProductsByRequest(Integer requestId){
        List<ProductsOfRequest> productsOfRL = productsOfRequestRepository.productsOfRequestByR(requestId);
        List<Product> products= new ArrayList<>();

        for (ProductsOfRequest req : productsOfRL) {
            Optional<Product> p= productRepository.findById(req.getProduct().getId());
            productsOfRequestRepository.delete(req);
            p.ifPresent(products::add);
        }
        return products;
    }

    public void cancelRequest(Integer requestId, Integer statusId){
        List<Product> products= getProductsByRequest(requestId);
        for(Product p : products){
            productRepository.updateProdductStatus(statusId,p.getId());
        }
    }

}
