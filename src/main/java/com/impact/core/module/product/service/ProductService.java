package com.impact.core.module.product.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.mapper.ProductMapper;
import com.impact.core.module.product.payload.request.ProductRequestDTO;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.product.repository.ProductRepository;
import com.impact.core.module.productStatus.enun.EProductStatus;
import com.impact.core.module.productStatus.service.ProductStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing {@link Product} entities.
 * <p>
 * This class contains the business logic for performing operations such as saving, updating, deleting,
 * and retrieving products, as well as interacting with product statuses and categories.
 */
@Service("productService")
@RequiredArgsConstructor
public class ProductService {
    public final ProductRepository productRepository;
    public final ProductMapper productMapper;
    private final ProductStatusService productStatusService;


    /**
     * Saves a list of products based on the provided {@link ProductRequestDTO}.
     * <p>
     * This method saves a specified quantity of products with the details provided in the request DTO.
     *
     * @param productRequestDTO the DTO containing the product details to save
     * @return a list of {@link ProductResponseDTO} objects
     *         representing the saved products
     */
    public ArrayList<ProductResponseDTO> save(ProductRequestDTO productRequestDTO) {
        int quantity = productRequestDTO.getQuantity();
        ArrayList<Product> products = new ArrayList<>();
        for(int i = 0; i < quantity; i++) {
            Product product = productMapper.toEntity(productRequestDTO);
            Product productSaved = productRepository.save(product);
            products.add(productSaved);
        }
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Updates an existing product based on the provided {@link ProductRequestDTO}.
     * <p>
     * This method updates the product with the specified {@code id} and returns the updated product as a DTO.
     *
     * @param id the ID of the product to update
     * @param productRequestDTO the DTO containing the updated product details
     * @return the updated {@link ProductResponseDTO}
     */
    public ProductResponseDTO update(int id, ProductRequestDTO productRequestDTO) {
        Product product = findProductById(id);
        Product productUpdated = productMapper.toEntity(productRequestDTO);
        productUpdated.setId(product.getId());
        Product productSaved = productRepository.save(productUpdated);
        return productMapper.toDTO(productSaved);
    }

    /**
     * Deletes a product based on its ID.
     * <p>
     * This method deletes the product with the specified {@code id} and returns the deleted product as a DTO.
     *
     * @param id the ID of the product to delete
     * @return the {@link ProductResponseDTO} of the deleted product
     */
    public ProductResponseDTO delete(int id) {
        Product product = findProductById(id);
        productRepository.delete(product);
        return productMapper.toDTO(product);
    }

    /**
     * Finds a product by its ID.
     * <p>
     * This method retrieves a product from the database by its ID. If the product does not exist, a
     * {@link ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the product to find
     * @return the {@link Product} entity
     * @throws ResourceNotFoundException if no product is found with the specified ID
     */
    public Product findProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el id: " + id + " no existe en la base de datos."));
    }

    /**
     * Retrieves all products from the database.
     * <p>
     * This method returns a list of all products, mapped to their corresponding DTOs.
     *
     * @return a list of {@link ProductResponseDTO} objects
     */
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the number of products available in inventory.
     * <p>
     * This method counts the products that have the "available" status and returns the count.
     *
     * @return the number of available products in the inventory
     */
    public Long remainingProductsInInventory() {
        return productRepository.remainingProducts(productStatusService.findByName(EProductStatus.PRODUCT_STATUS_AVAILABLE).getId());
    }
    // HELPER METHODS

    /**
     * Retrieves a list of available products from the specified category.
     * <p>
     * This method returns a limited number of products that are available and belong to the specified category.
     *
     * @param categoryName the name of the product category
     * @param quantity the number of products to retrieve
     * @return a list of available {@link Product} entities
     */
    public List<Product> getAvailableProductsByCategory(String categoryName, int quantity) {
        List<Product> products = productRepository.findAvailableProductsByCategory(categoryName);
        return products.stream().limit(quantity).collect(Collectors.toList());
    }

    /**
     * Updates the status of a product.
     * <p>
     * This method modifies the status of a product by updating the product's status in the database.
     *
     * @param status the new status ID to set for the product
     * @param product the ID of the product whose status will be updated
     */
    @Transactional
    public void updateStatus(Integer status, Integer product){
        productRepository.updateProdductStatus(status,product);
    }
}
