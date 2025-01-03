package com.impact.core.entities;

import com.impact.core.module.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_request")
public class ProductRequest {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private com.impact.core.entities.Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private com.impact.core.entities.ResourceRequestStatus status;

    @Lob
    @Column(name = "reason")
    private String reason;

}