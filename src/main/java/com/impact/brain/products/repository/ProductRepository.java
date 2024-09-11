package com.impact.brain.products.repository;

import com.impact.brain.products.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
