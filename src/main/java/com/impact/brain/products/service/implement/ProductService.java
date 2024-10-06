package com.impact.brain.products.service.implement;

import com.impact.brain.asset.assetRequest.entity.AssetRequest;
import com.impact.brain.asset.assetRequest.entity.ResourceRequestStatus;
import com.impact.brain.asset.assetRequest.repository.ResourceRequestStatusRepository;
import com.impact.brain.products.dto.ProductRequestDTO;
import com.impact.brain.products.entity.*;
import com.impact.brain.products.repository.*;
import com.impact.brain.products.service.IProductService;
import com.impact.brain.request.entity.Request;
import com.impact.brain.request.repository.RequestStatusRepository;
import com.impact.brain.request.service.implement.RequestService;
import com.impact.brain.user.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;


@org.springframework.stereotype.Service("productService")
public class ProductService implements IProductService {
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

    public Optional<CategoryType> findById(int id){ return categoryRepository.findById(id); }

    public Optional<UnitOfMeasurement> findByIdU(int id){ return unitMeasurementRepository.findById(id); }

    public ProductStatus findByNamePS(String name){ return productStatusRepository.findByName(name); }
    public Optional<ProductCategory> findByIdPC(int id){return productCategoryRepository.findById(id);}

    public void saveP(Product product){
        productRepository.save(product);
        System.out.println("Saving product: " + product);
    }

    @Override
    public ProductRequestDTO save(ProductRequestDTO productRequestDTO) {
        Request r = new Request();
        r.setDate(LocalDate.now());
        r.setUser(userService.findById(1)); // AGARRARLO DE LA SESSION
        r.setStatus(requestStatusRepository.findById(1).get()); // ESTADO POR DEFAULT
        int requestID = requestService.save(r).getId();
        // Verificar que el ID de la Request guardada no sea null
        System.out.println("RequestID: "+requestID);
        ProductRequest ar = toEntity(productRequestDTO);
        ar.setRequest(requestService.findById(requestID));
        ar.setStatus(resourceRequestStatusRepository.findById(1).get()); // ESTADO POR DEFAULT PENDIENTE
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
}
