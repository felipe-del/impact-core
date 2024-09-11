package com.impact.brain.products.controller;

import com.impact.brain.products.dto.ProductCategoryDTO;
import com.impact.brain.products.dto.ProductDTO;
import com.impact.brain.products.entity.*;
import com.impact.brain.products.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    Service service;
    @GetMapping("/types")
    public Iterable<CategorieType> read(){
        return service.types();
    }
    @GetMapping("/units")
    public Iterable<UnitOfMeasurement> readU(){
        return service.units();
    }

    @GetMapping("/categories")
    public Iterable<ProductCategory> readC(){
        return service.categories();
    }

    @PostMapping()
    public void create(@RequestBody ProductCategoryDTO category){
        try{
            ProductCategory categoryA= new ProductCategory();
            categoryA.setName(category.getName());
            categoryA.setCantidadMinima(category.getCantidadMinima());
            Optional<CategorieType> c= service.findById(category.getCategoryType());
            c.ifPresent(categoryA::setCategorieType);
            //CategorieType c= service.findById(category.getCategoryType());
            //categoryA.setCategorieTypeByCategorieType(c);

            Optional<UnitOfMeasurement> u= service.findByIdU(category.getUnit_of_measurement());
            u.ifPresent(categoryA::setUnitOfMeasurement);
            System.out.println(category.toString());
            System.out.println(categoryA.toString());
//            Optional<CategorieType> c= service.findById(category.get());
//            Optional<UnitOfMeasurement> u= service.findByIdU(category.getUnitOfMeasurement());
//            if(u.isPresent() && c.isPresent())
            service.saveC(categoryA);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/product")
    public void createP(@RequestBody ProductDTO product){
        try{
            for(int i=1; i<product.getQuantity();i++) {
                Product productA=new Product();
                productA.setPurchaseDate(product.getPurchaseDate());
                productA.setExpiryDate(product.getExpiryDate());
                Optional<ProductCategory> c= service.findByIdPC(product.getCategory());
                c.ifPresent(productA::setCategorie);
                ProductStatus s= service.findByNamePS("Available");
                if(s!=null) productA.setStatus(s);

                System.out.println(productA);

                service.saveP(productA);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

}
