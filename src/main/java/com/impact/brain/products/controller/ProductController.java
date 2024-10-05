package com.impact.brain.products.controller;

import com.impact.brain.products.dto.ProductCategoryCountDTO;
import com.impact.brain.products.dto.ProductCategoryDTO;
import com.impact.brain.products.dto.ProductDTO;
import com.impact.brain.products.entity.*;
import com.impact.brain.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/all")
    public Iterable<Product> all(){
        return productService.all();
    }
    @GetMapping("/types")
    public Iterable<CategoryType> read(){
        return productService.types();
    }
    @GetMapping("/units")
    public Iterable<UnitOfMeasurement> readU(){
        return productService.units();
    }

    @GetMapping("/categories")
    public Iterable<ProductCategory> readC(){
        return productService.categories();
    }

    @PostMapping()
    public void create(@RequestBody ProductCategoryDTO category){
        try{
            ProductCategory categoryA= productService.dto2ProductCategory(category);
            productService.saveC(categoryA);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/{id}")
    public Product read(@PathVariable int id){
        try{
            return productService.findByIdP(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/cat/{id}")
    public Optional<ProductCategory> readC(@PathVariable int id){
        try{
            return productService.findByIdPC(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/product")
    public void createP(@RequestBody ProductDTO product){
        try{
            for(int i = 1; i <= product.getQuantity(); i++) {
                Product productA=new Product();
                productA.setPurchaseDate(product.getPurchaseDate());
                productA.setExpiryDate(product.getExpiryDate());
                Optional<ProductCategory> c= productService.findByIdPC(product.getCategory());
                c.ifPresent(productA::setCategory);
                ProductStatus s= productService.findByNamePS("Disponible");
                if(s!=null) productA.setStatus(s);

                System.out.println(productA);

                productService.saveP(productA);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/cat")
    public Iterable<ProductCategoryCountDTO> getCategoryProductCounts() {
        // Obtener todas las categorías
        Iterable<ProductCategory> categories = productService.categories();

        // Crear una lista para almacenar los DTOs
        ArrayList<ProductCategoryCountDTO> categoryCountDTOs = new ArrayList<>();

        // Recorrer cada categoría para calcular el count y crear el DTO
        for (ProductCategory category : categories) {
            Long count = productService.productsCount(category.getId()); // Contar productos por categoría

            // Crear el DTO con los datos necesarios
            ProductCategoryCountDTO dto = new ProductCategoryCountDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setUnitOfMeasurement(category.getUnitOfMeasurement().getName());
            dto.setCantidadMinima(category.getCantidadMinima());
            dto.setAvailableQuantity(count);
            dto.setProductCategory(category.getCategorieType().getName());
            if(category.getCantidadMinima() >= count){
                dto.setStatus("Por solicitar");
            }else dto.setStatus("Suficiente");

            // Agregar el DTO a la lista
            categoryCountDTOs.add(dto);
        }

        // Retornar los DTOs al frontend
        return categoryCountDTOs;
    }

    @PutMapping("/category/{id}")
    public void updateC(@PathVariable int id, @RequestBody ProductCategoryDTO updatedCategory){
        try{
            productService.editCategory(updatedCategory);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/edit/{id}")
    public void updateP(@PathVariable int id, @RequestBody ProductDTO updatedProduct) {
        try {
            productService.editProduct(updatedProduct);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/status")
    public Iterable<ProductStatus> statusIterable(){
        return productService.getStatus();
    }

}
