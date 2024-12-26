package com.impact.core.entities;

import com.impact.core.module.assetModel.entity.AssetModel;
import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import com.impact.core.module.brand.entity.Brand;
import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.locationNumber.entity.LocationNumber;
import com.impact.core.module.supplier.entity.Supplier;
import com.impact.core.module.user.entity.User;
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
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private AssetSubcategory subcategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private com.impact.core.entities.AssetStatus status;

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
    private AssetModel assetModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_number_id")
    private LocationNumber locationNumber;

}