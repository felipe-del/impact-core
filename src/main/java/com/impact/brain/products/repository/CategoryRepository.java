package com.impact.brain.products.repository;

import com.impact.brain.products.entity.CategorieType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategorieType, Integer> {
    @Query("select c from CategorieType c where c.id= ?1")
    CategorieType findCategoryById(int id);
}