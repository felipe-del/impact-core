package com.impact.brain.products.repository;

import com.impact.brain.products.entity.CategoryType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryType, Integer> {
    @Query("select c from CategoryType c where c.id= ?1")
    CategoryType findCategoryById(int id);
}