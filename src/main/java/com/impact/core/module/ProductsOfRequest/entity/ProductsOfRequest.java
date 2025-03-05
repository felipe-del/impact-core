package com.impact.core.module.ProductsOfRequest.entity;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.productRequest.entity.ProductRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "products_of_request")
public class ProductsOfRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_request_id")
    private ProductRequest productRequest;

}
