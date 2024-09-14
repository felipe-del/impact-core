package com.impact.brain.products.service;

import com.impact.brain.products.entity.*;
import com.impact.brain.products.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@org.springframework.stereotype.Service("Service")
public class Service {
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
    public Iterable<CategorieType> types(){ return categoryRepository.findAll();}

    public Iterable<ProductCategory> categories() {return productCategoryRepository.findAll();}

    public void saveC(ProductCategory category){
        productCategoryRepository.save(category);
        System.out.println("Saving category: " + category);
    }

    public Optional<CategorieType> findById(int id){ return categoryRepository.findById(id); }

    public Optional<UnitOfMeasurement> findByIdU(int id){ return unitMeasurementRepository.findById(id); }

    public ProductStatus findByNamePS(String name){ return productStatusRepository.findByName(name); }
    public Optional<ProductCategory> findByIdPC(int id){return productCategoryRepository.findById(id);}

    public void saveP(Product product){
        productRepository.save(product);
        System.out.println("Saving product: " + product);
    }
}
