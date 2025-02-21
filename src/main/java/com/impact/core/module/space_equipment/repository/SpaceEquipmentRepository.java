package com.impact.core.module.space_equipment.repository;

import com.impact.core.module.space_equipment.entity.SpaceEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceEquipmentRepository extends JpaRepository<SpaceEquipment, Integer> {
}
