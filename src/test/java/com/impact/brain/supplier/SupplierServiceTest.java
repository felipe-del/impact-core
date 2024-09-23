package com.impact.brain.supplier;

import com.impact.brain.exception.ResourceNotFoundException;
import com.impact.brain.supplier.dto.SupplierDTO;
import com.impact.brain.supplier.entity.EntityType;
import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.repository.EntityTypeRepository;
import com.impact.brain.supplier.repository.SupplierRepository;
import com.impact.brain.supplier.service.ISupplierService;
import com.impact.brain.supplier.service.impl.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Isaac F. B. C.
 * @since 9/22/2024 - 6:27 PM
 */
/**
 * Clase de prueba para SupplierService para verificar su funcionalidad.
 */
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository; // Simulación del SupplierRepository para interacciones con la base de datos.

    @Mock
    private EntityTypeRepository entityTypeRepository; // Simulación del EntityTypeRepository para operaciones de tipo de entidad.

    @InjectMocks
    private SupplierService supplierService; // El servicio que se está probando, con simulaciones inyectadas.

    @BeforeEach
    void setUp() {
        // Inicializa las simulaciones antes de cada método de prueba.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSuppliers() {
        // Caso de prueba para verificar la recuperación de todos los proveedores.

        // Crea una lista de proveedores simulados.
        Supplier supplier1 = new Supplier();
        supplier1.setId(1);
        supplier1.setName("Supplier 1");

        Supplier supplier2 = new Supplier();
        supplier2.setId(2);
        supplier2.setName("Supplier 2");

        List<Supplier> supplierList = Arrays.asList(supplier1, supplier2);

        // Simulación del iterable de proveedores.
        Iterable<Supplier> suppliers = supplierList; // Puedes usar directamente la lista aquí.

        // Stub para el método del repositorio.
        when(supplierRepository.findAll()).thenReturn(suppliers);

        // Llamar al método para probar.
        var result = supplierService.getSuppliers();

        // Verificar que se llamó al repositorio.
        verify(supplierRepository, times(1)).findAll();
        assertNotNull(result); // Afirmar que el resultado no es nulo.
        assertEquals(2, result.size()); // Afirmar que se recuperaron 2 proveedores.
    }


    @Test
    void testSaveSupplier() {
        // Setup DTO with valid data
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(1);
        supplierDTO.setName("Supplier Name");
        supplierDTO.setPhone("1234567890");
        supplierDTO.setEmail("supplier@example.com");
        supplierDTO.setAddress("123 Supplier St.");
        supplierDTO.setIdNumber("ID123");
        supplierDTO.setEntityTypeId(1);

        // Mocking EntityType
        EntityType entityType = new EntityType();
        entityType.setId(1);

        // Stubbing repository methods
        when(entityTypeRepository.findById(1)).thenReturn(Optional.of(entityType));
        when(supplierRepository.save(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Mock save

        // Saving supplier
        Supplier savedSupplier = supplierService.save(supplierDTO);

        // Verifying interactions and assertions
        verify(entityTypeRepository, times(1)).findById(1);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
        assertNotNull(savedSupplier); // Should not be null
        assertEquals(supplierDTO.getName(), savedSupplier.getName());
        assertEquals(supplierDTO.getEmail(), savedSupplier.getEmail()); // Additional assertions
    }



    @Test
    void testGetById_SupplierExists() {
        // Caso de prueba para recuperar un proveedor por ID cuando existe.
        Supplier supplier = new Supplier();
        supplier.setId(1);
        supplier.setName("Existing Supplier");

        // Stub para el repositorio que devuelve el proveedor existente.
        when(supplierRepository.findById(1)).thenReturn(Optional.of(supplier));

        // Llamando al método del servicio.
        Supplier result = supplierService.getById(1);

        // Verificando la llamada al repositorio.
        verify(supplierRepository, times(1)).findById(1);
        assertEquals(supplier.getName(), result.getName()); // Verificar el nombre.
    }

    @Test
    void testGetById_SupplierNotFound() {
        // Caso de prueba para intentar recuperar un proveedor por ID cuando no existe.
        when(supplierRepository.findById(1)).thenReturn(Optional.empty()); // Stub para proveedor no existente.

        // Verificando que se lanza la excepción cuando se busca un proveedor que no existe.
        assertThrows(ResourceNotFoundException.class, () -> {
            supplierService.getById(1);
        });

        // Verificando la llamada al repositorio.
        verify(supplierRepository, times(1)).findById(1);
    }

}
