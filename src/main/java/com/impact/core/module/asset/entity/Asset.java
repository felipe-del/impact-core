package com.impact.core.module.asset.entity;

import com.impact.core.module.assetModel.entity.AssetModel;
import com.impact.core.module.assetStatus.entity.AssetStatus;
import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import com.impact.core.module.brand.entity.Brand;
import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.locationNumber.entity.LocationNumber;
import com.impact.core.module.supplier.entity.Supplier;
import com.impact.core.module.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing a physical or intangible asset within the system.
 * Each asset has associated metadata such as value, purchase date,
 * responsible user, model, brand, and current status.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "asset")
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    /**
     * Unique identifier for the asset
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Date when the asset was purchased
     */
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    /**
     * Monetary value of the asset
     */
    @Column(name = "value", precision = 10, scale = 2)
    private BigDecimal value;

    /**
     * {@link User} responsible for the asset
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id")
    private User responsible;

    /**
     * {@link Supplier} from which this asset was acquired
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    /**
     * {@link AssetSubcategory} under which the asset is clasified
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private AssetSubcategory subcategory;

    /**
     * {@link Brand} associated with the asset
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    /**
     * Current operational or inventory status ({@link AssetStatus}) of the asset
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private AssetStatus status;

    /**
     * Logical deletion flag for soft-delete scenarios
     */
    @ColumnDefault("0")
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * Manufacturer's series or serial number of the asset
     */
    @Size(max = 50)
    @Column(name = "asset_series", length = 50)
    private String assetSeries;

    /**
     * Unique plate number associated with the asset
     */
    @Size(max = 50)
    @Column(name = "plate_number", length = 50)
    private String plateNumber;

    /**
     * {@link AssetModel} associated with the asset
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_model_id")
    private AssetModel assetModel;

    /**
     * {@link Currency} used to represent the asset's value
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    /**
     * {@link LocationNumber} associated with the asset
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_number_id")
    private LocationNumber locationNumber;

}