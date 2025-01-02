package com.impact.core.module.productCategory.repository;

import com.impact.core.module.productCategory.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {

}
