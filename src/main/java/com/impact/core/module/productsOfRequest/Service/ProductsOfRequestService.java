package com.impact.core.module.productsOfRequest.Service;

import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productsOfRequest.entity.ProductsOfRequest;
import com.impact.core.module.productsOfRequest.repository.ProductsOfRequestRepository;
import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing operations related to {@link ProductsOfRequest} entities.
 * <p>
 * This service provides methods for handling the business logic surrounding product requests,
 * including retrieving products by request, canceling requests, and saving new {@link ProductsOfRequest} records.
 * </p>
 */
@Service("productsOfRequestService")
@RequiredArgsConstructor
public class ProductsOfRequestService {

    @Autowired
    ProductsOfRequestRepository productsOfRequestRepository;
    @Autowired
    ProductRepository productRepository;

    /**
     * Retrieves a list of {@link Product} entities associated with a specific request.
     * <p>
     * This method fetches the list of {@link ProductsOfRequest} by {@code requestId}, retrieves
     * the corresponding {@link Product} entities, and deletes the associated {@link ProductsOfRequest} records.
     * </p>
     *
     * @param requestId the ID of the {@link ProductRequest} to fetch products for.
     * @return a list of {@link Product} entities related to the given {@code requestId}.
     */
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

    /**
     * Cancels a specific {@link ProductRequest} by updating the status of its related {@link Product} entities.
     * <p>
     * This method retrieves the products associated with the given {@code requestId}, and for each product,
     * updates its status using the provided {@code statusId}.
     * </p>
     *
     * @param requestId the ID of the {@link ProductRequest} to cancel.
     * @param statusId the ID of the status to update the {@link Product} entities to.
     */
    public void cancelRequest(Integer requestId, Integer statusId){
        List<Product> products= getProductsByRequest(requestId);
        for(Product p : products){
            productRepository.updateProdductStatus(statusId,p.getId());
        }
    }

    /**
     * Saves a new {@link ProductsOfRequest} entity to the database.
     *
     * @param productsOfRequest the {@link ProductsOfRequest} entity to save.
     * @return the saved {@link ProductsOfRequest} entity.
     */
    public ProductsOfRequest save(ProductsOfRequest productsOfRequest){
        return productsOfRequestRepository.save(productsOfRequest);
    }

}
