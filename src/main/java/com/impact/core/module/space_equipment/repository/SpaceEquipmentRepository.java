package com.impact.core.module.space_equipment.repository;

import com.impact.core.module.space_equipment.entity.SpaceEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link SpaceEquipment} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations for {@link SpaceEquipment} entities.
 * Spring Data JPA automatically implements the repository, allowing interaction with the database.
 * </p>
 */
@Repository
public interface SpaceEquipmentRepository extends JpaRepository<SpaceEquipment, Integer> {
}
