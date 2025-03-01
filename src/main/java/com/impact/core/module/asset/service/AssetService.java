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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<SumOfCurrency> getInventoryValue(LocalDate start_date, LocalDate end_date) {
        List<SumOfCurrency> sumOfCurrencies = new ArrayList<>();

        for (Object[] currencyValues : assetRepository.inventoryValueInAPeriod(start_date, end_date) ) {
            SumOfCurrency toAdd = new SumOfCurrency();

            toAdd.setCurrency(assetMapper.currencyMapper.toDTO((Currency) currencyValues[0]));
            toAdd.setAmount((BigDecimal)  currencyValues[1]);

            sumOfCurrencies.add(toAdd);
        }

        return sumOfCurrencies;
    }

    public List<AssetResponse> findAll() {
        return assetRepository.findAll().stream()
                .map(assetMapper::toDTO)
                .toList();
    }

}
