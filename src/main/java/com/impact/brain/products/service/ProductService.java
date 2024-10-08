package com.impact.brain.products.service;

import com.impact.brain.request.repository.ResourceRequestStatusRepository;
import com.impact.brain.products.dto.ProductCategoryDTO;
import com.impact.brain.products.dto.ProductDTO;
import com.impact.brain.products.dto.ProductRequestDTO;
import com.impact.brain.products.entity.*;
import com.impact.brain.products.repository.*;
import com.impact.brain.request.entity.Request;
import com.impact.brain.request.repository.RequestStatusRepository;
import com.impact.brain.request.service.implement.RequestService;
import com.impact.brain.user.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service("productService")
public class ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UnitMeasurementRepository unitMeasurementRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;

    private final UserService userService;
    private final RequestStatusRepository requestStatusRepository;
    private final RequestService requestService;
    private final ProductRequestRepository productRequestRepository;
    private final ResourceRequestStatusRepository resourceRequestStatusRepository;

    public ProductService(UserService userService,
                          RequestStatusRepository requestStatusRepository,
                          RequestService requestService,
                          ProductRequestRepository productRequestRepository,
                          ResourceRequestStatusRepository resourceRequestStatusRepository
    ) {
        this.userService = userService;
        this.requestStatusRepository = requestStatusRepository;
        this.requestService = requestService;
        this.productRequestRepository = productRequestRepository;
        this.resourceRequestStatusRepository = resourceRequestStatusRepository;
    }

    public Iterable<Product> all(){return productRepository.findAll();}
    public Iterable<UnitOfMeasurement> units(){ return unitMeasurementRepository.findAll(); }
    public Iterable<CategoryType> types(){ return categoryRepository.findAll();}

    public Iterable<ProductCategory> categories() {return productCategoryRepository.findAll();}
    public Long productsCount(int id){return productRepository.countProductsByCategoryId(id);}
    public void saveC(ProductCategory category){
        productCategoryRepository.save(category);
        System.out.println("Saving category: " + category);
    }
    public Product findByIdP(int id){ return productRepository.findById(id).orElse(null);}
    public Optional<CategoryType> findById(int id){ return categoryRepository.findById(id); }

    public Optional<UnitOfMeasurement> findByIdU(int id){ return unitMeasurementRepository.findById(id); }

    public ProductStatus findByNamePS(String name){ return productStatusRepository.findByName(name); }
    public Optional<ProductCategory> findByIdPC(int id){return productCategoryRepository.findById(id);}

    public void saveP(Product product){
        productRepository.save(product);
        System.out.println("Saving product: " + product);
    }
    public Product dto2Product(ProductDTO dto){
        Product productA=new Product();
        productA.setPurchaseDate(dto.getPurchaseDate());
        productA.setExpiryDate(dto.getExpiryDate());
        Optional<ProductCategory> c= findByIdPC(dto.getCategory());
        c.ifPresent(productA::setCategory);
        ProductStatus s= findByNamePS("Disponible");
        if(s!=null) productA.setStatus(s);

        return productA;
    }
    public void editProduct(ProductDTO dto){
        Product productA= findByIdP(dto.getId());
        if(productA!=null){
            productA.setPurchaseDate(dto.getPurchaseDate());
            productA.setExpiryDate(dto.getExpiryDate());
        }
        saveP(productA);
    }
    public void editCategory(ProductCategoryDTO dto){
        Optional<ProductCategory> categoryOptional = findByIdPC(dto.getId());
        categoryOptional .ifPresent( categoryA -> {
            categoryA.setName(dto.getName());
            categoryA.setCantidadMinima(dto.getCantidadMinima());

            Optional<CategoryType> c= findById(dto.getCategoryType());
            c.ifPresent(categoryA::setCategoryType);
            Optional<UnitOfMeasurement> u= findByIdU(dto.getUnit_of_measurement());
            u.ifPresent(categoryA::setUnitOfMeasurement);

            saveC(categoryA);
        });
    }

    public ProductCategory dto2ProductCategory(ProductCategoryDTO category){
        ProductCategory categoryA= new ProductCategory();
        categoryA.setName(category.getName());
        categoryA.setCantidadMinima(category.getCantidadMinima());

        Optional<CategoryType> c= this.findById(category.getCategoryType());
        c.ifPresent(categoryA::setCategoryType);
        Optional<UnitOfMeasurement> u= this.findByIdU(category.getUnit_of_measurement());
        u.ifPresent(categoryA::setUnitOfMeasurement);

        return categoryA;
    }

    public Iterable<ProductStatus> getStatus(){
        return productStatusRepository.findAll();
    }


    public ProductRequestDTO save(ProductRequestDTO productRequestDTO) {
        // Crear y guardar una nueva Request
        Request r = new Request();
        r.setDate(LocalDate.now());
        r.setUser(userService.findById(1)); // AGARRARLO DE LA SESSION
        r.setStatus(requestStatusRepository.findById(1).get()); // ESTADO POR DEFAULT
        int requestID = requestService.save(r).getId();

        // Verificar que el ID de la Request guardada no sea null
        System.out.println("RequestID: " + requestID);

        // Convertir el DTO en la entidad ProductRequest
        ProductRequest ar = toEntity(productRequestDTO);
        ar.setRequest(requestService.findById(requestID));
        ar.setStatus(resourceRequestStatusRepository.findById(1).get()); // ESTADO POR DEFAULT PENDIENTE

        // Obtener la cantidad solicitada de productos
        int requestedQuantity = productRequestDTO.getCount();

        // Buscar los productos disponibles de la categoría solicitada
        List<Product> availableProducts = getAvailableProductsByCategory(productRequestDTO.getCategoryName(),requestedQuantity);

        // Verificar si hay suficientes productos disponibles
        if (availableProducts.size() < requestedQuantity) {
            throw new IllegalArgumentException("No hay suficientes productos disponibles en esta categoría.");
        }

        // Cambiar el estado de los productos seleccionados
        availableProducts.stream().limit(requestedQuantity).forEach(product -> {
            product.setStatus(productStatusRepository.findById(2).get()); // Cambiar el estado a "reservado" o el estado que corresponda
            productRepository.save(product); // Guardar el producto con el nuevo estado
        });

        // Guardar el ProductRequest en la base de datos
        return toDto(productRequestRepository.save(ar));
    }


    private ProductRequest toEntity(ProductRequestDTO productRequestDTO) {
        ProductRequest pr = new ProductRequest();
        pr.setId(0); // Lo genera la BD
        pr.setRequest(null); // se lo seteamos despues
        pr.setProduct(productRepository.findById(productRequestDTO.getProductId()).get());
        pr.setStatus(null); // lo seteamos despues
        pr.setReason(productRequestDTO.getReason());
        return pr;
    }

    private ProductRequestDTO toDto(ProductRequest productRequest) {
        ProductRequestDTO pr = new ProductRequestDTO();
        pr.setProductId(productRequest.getId());
        pr.setCategoryName(productRequest.getProduct().getCategory().getName());
        pr.setStatusId(0); // Lo seteamos despues
        pr.setReason(productRequest.getReason());
        pr.setRequestId(productRequest.getRequest().getId());
        return pr;
    }
    public List<Product> getAvailableProductsByCategory(String categoryName, int limit) {
        List<Product> products = productRepository.findAvailableProductsByCategory(categoryName);
        return products.stream().limit(limit).collect(Collectors.toList());
    }

}
