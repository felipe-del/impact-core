package com.impact.core.module.asset.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.asset.entity.Asset;
import com.impact.core.module.asset.mapper.AssetMapper;
import com.impact.core.module.asset.payload.request.AssetRequest;
import com.impact.core.module.asset.payload.response.AssetResponse;
import com.impact.core.module.asset.repository.AssetRepository;
import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.payload.response.SumOfCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service layer for managing operations related to assets.
 * It acts as an intermediary between controllers and repositories.
 */
@Service("assetService")
@RequiredArgsConstructor
public class AssetService {
    public final AssetRepository assetRepository;
    public final AssetMapper assetMapper;

    /**
     * Saves a new asset to the database.
     *
     * @param assetRequest the asset data to be saved
     * @return the saved asset as a DTO
     */
    public AssetResponse save(AssetRequest assetRequest) {
        Asset asset = assetMapper.toEntity(assetRequest);
        Asset savedAsset = assetRepository.save(asset);
        return assetMapper.toDTO(savedAsset);
    }

    /**
     * Updates an existing asset identified by its ID.
     *
     * @param id           the ID of the asset to update
     * @param assetRequest the updated asset data
     * @return the updated asset as a DTO
     */
    public AssetResponse update(int id, AssetRequest assetRequest) {
        Asset asset = this.findById(id);
        Asset updateAsset = assetMapper.toEntity(assetRequest);
        updateAsset.setId(asset.getId());
        Asset savedAsset = assetRepository.save(updateAsset);
        return assetMapper.toDTO(savedAsset);
    }

    /**
     * Deletes the asset with the specified ID.
     *
     * @param id the ID of the asset to delete
     * @return the deleted asset as a DTO
     */
    public AssetResponse delete(int id) {
        Asset asset = this.findById(id);
        assetRepository.delete(asset);
        return assetMapper.toDTO(asset);
    }

    /**
     * Finds an asset by its ID or throws a ResourceNotFoundException if not found.
     *
     * @param id the ID of the asset
     * @return the Asset entity
     */
    public Asset findById(int id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El activo con el id: " + id + " no existe."));
    }

    /**
     * Retrieves an asset by its ID and maps it to a DTO.
     *
     * @param id the ID of the asset
     * @return the asset as a DTO
     */
    public AssetResponse findByIdR(int id) {
        Asset asset = this.findById(id);
        return assetMapper.toDTO(asset);
    }

    /**
     * Retrieves the total inventory value grouped by currency over a given date range.
     *
     * @param start_date the start date for the inventory period
     * @param end_date   the end date for the inventory period
     * @return list of currency-wise summed values and asset counts
     */
    public List<SumOfCurrency> getInventoryValue(LocalDate start_date, LocalDate end_date) {
        List<SumOfCurrency> sumOfCurrencies = new ArrayList<>();

        for (Object[] currencyValues : assetRepository.inventoryValueInAPeriod(start_date, end_date) ) {
            SumOfCurrency toAdd = new SumOfCurrency();

            toAdd.setCurrency(assetMapper.currencyMapper.toDTO((Currency) currencyValues[0]));
            toAdd.setAmount((BigDecimal)  currencyValues[1]);
            toAdd.setQuantity((Long) currencyValues[2]);

            sumOfCurrencies.add(toAdd);
        }

        return sumOfCurrencies;
    }

    /**
     * Retrieves all assets and maps them to their DTO representations.
     *
     * @return list of all asset DTOs
     */
    public List<AssetResponse> findAll() {
        return assetRepository.findAll().stream()
                .map(assetMapper::toDTO)
                .toList();
    }

    /**
     * Updates the status of an asset by its plate number.
     *
     * @param status the new status ID to set
     * @param asset  the plate number of the asset
     */
    @Transactional
    public void updateStatus(Integer status, String asset){
        assetRepository.updateAssetStatus(status,asset);
    }


    /**
     * Groups assets by their purchase month and year within a given date range.
     *
     * @param startDate the start date for filtering
     * @param endDate   the end date for filtering
     * @return map of formatted "MMM yyyy" date strings to asset count
     */
    public Map<String, Long> getAssetsByPurchaseDate(LocalDate startDate, LocalDate endDate) {
        List<Asset> assets = assetRepository.findAllByPurchaseDateBetween(startDate, endDate);

        // Count assets grouped by month and year
        Map<YearMonth, Long> monthlyCounts = assets.stream()
                .collect(Collectors.groupingBy(
                        asset -> YearMonth.from(asset.getPurchaseDate()),
                        Collectors.counting()
                ));

        // Convert to a readable format for the frontend (e.g., "Jan 2024")
        return monthlyCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()) + " " + entry.getKey().getYear(),
                        Map.Entry::getValue
                ));
    }

}
