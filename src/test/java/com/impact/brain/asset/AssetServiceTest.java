package com.impact.brain.asset;

import com.impact.brain.asset.dto.AssetDTO;
import com.impact.brain.asset.entity.*;
import com.impact.brain.asset.repository.*;
import com.impact.brain.asset.service.impl.AssetService;
import com.impact.brain.brand.entity.Brand;
import com.impact.brain.brand.repository.BrandRepository;
import com.impact.brain.exception.ResourceNotFoundException;
import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.service.impl.SupplierService;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * @author Isaac F. B. C.
 * @since 9/22/2024 - 7:00 PM
 */

/**
 * Clase de prueba para AssetService para verificar su funcionalidad.
 */
public class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository; // Simulación del AssetRepository para interacciones con la base de datos.

    @Mock
    private AssetStatusRepository assetStatusRepository; // Simulación del AssetStatusRepository para operaciones de estado de activo.

    @Mock
    private AssetSubcategoryRepository assetSubcategoryRepository; // Simulación del AssetSubcategoryRepository.

    @Mock
    private CurrencyRepository currencyRepository; // Simulación del AssetCurrencyRepository.

    @Mock
    private AssetModelRepository assetModelRepository; // Simulación del AssetModelRepository.

    @Mock
    private SupplierService supplierService; // Simulación del SupplierService.

    @Mock
    private BrandRepository brandRepository; // Simulación del BrandRepository.

    @Mock
    private UserService userService; // Simulación del UserService.

    @InjectMocks
    private AssetService assetService; // El servicio que se está probando, con simulaciones inyectadas.


    @BeforeEach
    void setUp() {
        // Inicializa las simulaciones antes de cada método de prueba.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAssets() {
        // Caso de prueba para verificar la recuperación de todos los activos.

        // Crea una lista de activos simulados.
        Asset asset1 = new Asset();
        asset1.setId(1);
        asset1.setAssetSeries("Series 1");

        Asset asset2 = new Asset();
        asset2.setId(2);
        asset2.setAssetSeries("Series 2");

        List<Asset> assetList = Arrays.asList(asset1, asset2);

        // Simulación del repositorio de activos.
        when(assetRepository.findAll()).thenReturn(assetList);

        // Llamar al método para probar.
        var result = assetService.all();

        // Verificar que se llamó al repositorio.
        verify(assetRepository, times(1)).findAll();
        assertNotNull(result); // Afirmar que el resultado no es nulo.
        assertEquals(2, ((List<Asset>) result).size()); // Afirmar que se recuperaron 2 activos.
    }

    @Test
    void testSaveAsset() {
        // Setup DTO with valid data
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        assetDTO.setAssetSeries("Series Test");
        assetDTO.setValue(BigDecimal.valueOf(1000));
        assetDTO.setSupplierId(1);
        assetDTO.setBrandId(1);
        assetDTO.setSubcategoryId(1);
        assetDTO.setResponsibleId(1);
        assetDTO.setStatusId(1);
        assetDTO.setCurrencyId(1);
        assetDTO.setAssetModelId(1);

        // Mocking the dependencies
        Supplier supplier = new Supplier();
        supplier.setId(1);
        when(supplierService.getById(1)).thenReturn(supplier);

        Brand brand = new Brand();
        brand.setId(1);
        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        AssetSubcategory subcategory = new AssetSubcategory();
        subcategory.setId(1);
        when(assetSubcategoryRepository.findById(1)).thenReturn(Optional.of(subcategory));

        User user = new User();
        user.setId(1);
        when(userService.findById(1)).thenReturn(user);

        AssetStatus status = new AssetStatus();
        status.setId(1);
        when(assetStatusRepository.findById(1)).thenReturn(Optional.of(status));

        Currency currency = new Currency();
        currency.setCurrencyName("USD");
        when(currencyRepository.findByCurrencyName("USD")).thenReturn(currency);

        AssetModel assetModel = new AssetModel();
        assetModel.setModelName("Model Test");
        when(assetModelRepository.findByModelName("Model Test")).thenReturn(assetModel);

        // Stubbing the repository to save the asset.
        when(assetRepository.save(any(Asset.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Simulate save method

        // Save the asset
        Asset savedAsset = assetService.save(assetDTO);

        // Verifying interactions and assertions
        verify(assetRepository, times(1)).save(any(Asset.class));
        assertNotNull(savedAsset); // Should not be null
        assertEquals(assetDTO.getAssetSeries(), savedAsset.getAssetSeries());
        assertEquals(supplier, savedAsset.getSupplier());
        assertEquals(brand, savedAsset.getBrand());
        assertEquals(subcategory, savedAsset.getSubcategory());
        assertEquals(user, savedAsset.getResponsible());
        assertEquals(status, savedAsset.getStatus());
        assertEquals(currency, savedAsset.getCurrency());
        assertEquals(assetModel, savedAsset.getAssetModel());
    }


    @Test
    void testGetById_AssetExists() {
        // Caso de prueba para recuperar un activo por ID cuando existe.
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetSeries("Existing Asset");

        // Stub para el repositorio que devuelve el activo existente.
        when(assetRepository.findById(1)).thenReturn(Optional.of(asset));

        // Llamando al método del servicio.
        Asset result = assetService.getById(1);

        // Verificando la llamada al repositorio.
        verify(assetRepository, times(1)).findById(1);
        assertEquals(asset.getAssetSeries(), result.getAssetSeries()); // Verificar la serie del activo.
    }

    @Test
    void testGetById_AssetNotFound() {
        // Caso de prueba para intentar recuperar un activo por ID cuando no existe.
        when(assetRepository.findById(1)).thenReturn(Optional.empty()); // Stub para activo no existente.

        // Verificando que se lanza la excepción cuando se busca un activo que no existe.
        assertThrows(ResourceNotFoundException.class, () -> {
            assetService.getById(1);
        });

        // Verificando la llamada al repositorio.
        verify(assetRepository, times(1)).findById(1);
    }
}
