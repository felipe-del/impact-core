package com.impact.brain.products.service;

import com.impact.brain.products.entity.CategorieType;
import com.impact.brain.products.entity.ProductCategory;
import com.impact.brain.products.repository.ProductCategoryRepository;
import com.impact.brain.products.repository.UnitMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.impact.brain.products.repository.CategoryRepository;
import com.impact.brain.products.entity.UnitOfMeasurement;

import java.util.Optional;


@org.springframework.stereotype.Service("Service")
public class Service {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UnitMeasurementRepository unitMeasurementRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public Iterable<UnitOfMeasurement> units(){ return unitMeasurementRepository.findAll(); }
    public Iterable<CategorieType> types(){ return categoryRepository.findAll();}

    public Iterable<ProductCategory> categories() {return productCategoryRepository.findAll();}

    public void saveC(ProductCategory category){
        productCategoryRepository.save(category);
        System.out.println("Saving category: " + category);
    }

    public Optional<CategorieType> findById(int id){ return categoryRepository.findById(id); }

    public Optional<UnitOfMeasurement> findByIdU(int id){ return unitMeasurementRepository.findById(id); }

}
