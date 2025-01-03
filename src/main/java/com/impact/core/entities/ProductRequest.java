package com.impact.core.entities;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
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
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ResourceRequestStatus status;

    @Lob
    @Column(name = "reason")
    private String reason;

}