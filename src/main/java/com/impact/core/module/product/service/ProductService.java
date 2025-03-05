package com.impact.core.module.product.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.mapper.ProductMapper;
import com.impact.core.module.product.payload.request.ProductRequestDTO;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("productService")
@RequiredArgsConstructor
public class ProductService {
    public final ProductRepository productRepository;
    public final ProductMapper productMapper;

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

    public ProductResponseDTO update(int id, ProductRequestDTO productRequestDTO) {
        Product product = findProductById(id);
        Product productUpdated = productMapper.toEntity(productRequestDTO);
        productUpdated.setId(product.getId());
        Product productSaved = productRepository.save(productUpdated);
        return productMapper.toDTO(productSaved);
    }

    public ProductResponseDTO delete(int id) {
        Product product = findProductById(id);
        productRepository.delete(product);
        return productMapper.toDTO(product);
    }

    public Product findProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el id: " + id + " no existe en la base de datos."));
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    // HELPER METHODS

    public List<Product> getAvailableProductsByCategory(String categoryName, int quantity) {
        List<Product> products = productRepository.findAvailableProductsByCategory(categoryName);
        return products.stream().limit(quantity).collect(Collectors.toList());
    }
    @Transactional
    public void updateStatus(Integer status, Integer product){
        productRepository.updateProdductStatus(status,product);
    }
}
