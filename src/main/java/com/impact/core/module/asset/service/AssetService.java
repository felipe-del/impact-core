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

@Service("assetService")
@RequiredArgsConstructor
public class AssetService {
    public final AssetRepository assetRepository;
    public final AssetMapper assetMapper;

    public AssetResponse save(AssetRequest assetRequest) {
        Asset asset = assetMapper.toEntity(assetRequest);
        Asset savedAsset = assetRepository.save(asset);
        return assetMapper.toDTO(savedAsset);
    }

    public AssetResponse update(int id, AssetRequest assetRequest) {
        Asset asset = this.findById(id);
        Asset updateAsset = assetMapper.toEntity(assetRequest);
        updateAsset.setId(asset.getId());
        Asset savedAsset = assetRepository.save(updateAsset);
        return assetMapper.toDTO(savedAsset);
    }

    public AssetResponse delete(int id) {
        Asset asset = this.findById(id);
        assetRepository.delete(asset);
        return assetMapper.toDTO(asset);
    }

    public Asset findById(int id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El activo con el id: " + id + " no existe."));
    }

    public AssetResponse findByIdR(int id) {
        Asset asset = this.findById(id);
        return assetMapper.toDTO(asset);
    }

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

    public List<AssetResponse> findAll() {
        return assetRepository.findAll().stream()
                .map(assetMapper::toDTO)
                .toList();
    }

    @Transactional
    public void updateStatus(Integer status, String asset){
        assetRepository.updateAssetStatus(status,asset);
    }


    public Map<String, Long> getAssetsByPurchaseDate(LocalDate startDate, LocalDate endDate) {
        List<Asset> assets = assetRepository.findAllByPurchaseDateBetween(startDate, endDate);

        // Contar los activos por mes y año
        Map<YearMonth, Long> monthlyCounts = assets.stream()
                .collect(Collectors.groupingBy(
                        asset -> YearMonth.from(asset.getPurchaseDate()),
                        Collectors.counting()
                ));

        // Convertir a formato String para el frontend (Ejemplo: "Ene 2024")
        return monthlyCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()) + " " + entry.getKey().getYear(),
                        Map.Entry::getValue
                ));
    }

}
