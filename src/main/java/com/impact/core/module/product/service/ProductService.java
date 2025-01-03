package com.impact.core.module.product.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.mapper.ProductMapper;
import com.impact.core.module.product.payload.request.ProductRequest;
import com.impact.core.module.product.payload.response.ProductResponse;
import com.impact.core.module.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("productService")
@RequiredArgsConstructor
public class ProductService {
    public final ProductRepository productRepository;
    public final ProductMapper productMapper;

    public ProductResponse save(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        Product productSaved = productRepository.save(product);
        return productMapper.toDTO(productSaved);
    }

    public ProductResponse update(int id, ProductRequest productRequest) {
        Product product = findProductById(id);
        Product productUpdated = productMapper.toEntity(productRequest);
        productUpdated.setId(product.getId());
        Product productSaved = productRepository.save(productUpdated);
        return productMapper.toDTO(productSaved);
    }

    public ProductResponse delete(int id) {
        Product product = findProductById(id);
        productRepository.delete(product);
        return productMapper.toDTO(product);
    }

    public Product findProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el id: " + id + " no existe en la base de datos."));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }
}
