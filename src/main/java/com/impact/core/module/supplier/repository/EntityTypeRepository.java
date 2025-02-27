package com.impact.core.module.supplier.repository;

import com.impact.core.module.supplier.entity.EntityType;
import com.impact.core.module.supplier.enun.EEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType, Long> {
    Optional<EntityType> findByTypeName(EEntityType name);
}
