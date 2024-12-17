package com.impact.brain.entities;

import com.impact.brain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "asset")
public class Asset {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "value", precision = 10, scale = 2)
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id")
    private User responsible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private com.impact.brain.entities.Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private com.impact.brain.entities.AssetSubcategory subcategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private com.impact.brain.entities.Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private com.impact.brain.entities.AssetStatus status;

    @ColumnDefault("0")
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Size(max = 50)
    @Column(name = "asset_series", length = 50)
    private String assetSeries;

    @Size(max = 50)
    @Column(name = "plate_number", length = 50)
    private String plateNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_model_id")
    private com.impact.brain.entities.AssetModel assetModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private com.impact.brain.entities.Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_number_id")
    private com.impact.brain.entities.LocationNumber locationNumber;

}