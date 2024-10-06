package com.impact.brain.products.service;

import com.impact.brain.products.dto.ProductCategoryDTO;
import com.impact.brain.products.dto.ProductDTO;
import com.impact.brain.products.entity.*;
import com.impact.brain.products.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


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
}
