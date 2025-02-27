package com.impact.core.module.productStatus.entity;

import com.impact.core.module.productStatus.enun.EProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "product_status")
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatus {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EProductStatus name;

    @Lob
    @Column(name = "description")
    private String description;

}