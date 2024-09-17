package com.impact.brain.products.service;

import com.impact.brain.products.entity.*;
import com.impact.brain.products.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UnitMeasurementRepository unitMeasurementRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductStatusRepository productStatusRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProduct() {
        // Crear un producto ficticio
        Product product = new Product();
        product.setId(1);  // Simulamos que el ID sería autogenerado, pero lo configuramos por el test
        product.setPurchaseDate(LocalDate.now());
        product.setExpiryDate(LocalDate.now().plusDays(60));
        product.setStatus(new ProductStatus());
        ProductCategory cat=new ProductCategory();
        cat.setId(1);
        cat.setName("Jabón abrasivo en polvo (Ajax)");
        cat.setCantidadMinima(15);
        cat.setUnitOfMeasurement(new UnitOfMeasurement());
        cat.setCategoryType(new CategoryType());
        Mockito.when((productCategoryRepository.findById(1))).thenReturn(Optional.of(cat));
        Optional<ProductCategory> c= productService.findByIdPC(1);
        c.ifPresent(product::setCategory);
        ProductStatus s= productService.findByNamePS("Available");
        if(s!=null) product.setStatus(s);


        // Simular el comportamiento del repositorio al guardar el producto
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        // Llamar al método que queremos testear
        productService.saveP(product);

        // Verificar que el repositorio haya sido llamado correctamente
        Mockito.verify(productRepository, Mockito.times(1)).save(product);

        // Verificar que el producto no sea nulo después de guardar
        Assertions.assertNotNull(product);

        // Puedes agregar más aserciones según lo que esperes que suceda al guardar un producto
        System.out.println("Producto guardado: " + product);
    }
    @Test
    void testSaveProductCategoryL() {
        ProductCategory category = new ProductCategory();
        category.setId(1);
        category.setName("Jabón abrasivo en polvo (Ajax)");
        category.setCantidadMinima(15);
        CategoryType cat=new CategoryType();
        cat.setId(1);
        cat.setName("Limpieza");
        cat.setDescription("Productos de limpieza");
        category.setUnitOfMeasurement(new UnitOfMeasurement());
        Mockito.when((categoryRepository.findById(1))).thenReturn(Optional.of(cat));
        Optional<CategoryType> c= productService.findById(1);
        c.ifPresent(category::setCategoryType);
        productService.saveC(category);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).save(category);
    }
    @Test
    void testSaveProductCategoryO() {
        ProductCategory category = new ProductCategory();
        category.setId(1);
        category.setName("Jabón abrasivo en polvo (Ajax)");
        category.setCantidadMinima(15);
        CategoryType cat=new CategoryType();
        cat.setId(2);
        cat.setName("Oficina");
        cat.setDescription("Productos de oficina");
        category.setUnitOfMeasurement(new UnitOfMeasurement());
        Mockito.when((categoryRepository.findById(2))).thenReturn(Optional.of(cat));
        Optional<CategoryType> c= productService.findById(2);
        c.ifPresent(category::setCategoryType);
        productService.saveC(category);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).save(category);
    }
    @Test
    void testAllProducts() {
        // Configuración
        Iterable<Product> products = mock(Iterable.class);
        when(productRepository.findAll()).thenReturn(products);

        // Ejecución
        Iterable<Product> result = productService.all();

        // Verificación
        assertNotNull(result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        int id = 1;
        Optional<CategoryType> category = Optional.of(new CategoryType());
        Mockito.when(categoryRepository.findById(id)).thenReturn(category);
        Optional<CategoryType> result = productService.findById(id);
        Assertions.assertTrue(result.isPresent());
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testFindByNameProductStatus() {
        String name = "Active";
        ProductStatus status = new ProductStatus();
        Mockito.when(productStatusRepository.findByName(name)).thenReturn(status);
        ProductStatus result = productService.findByNamePS(name);
        Assertions.assertNotNull(result);
        Mockito.verify(productStatusRepository, Mockito.times(1)).findByName(name);
    }



}
