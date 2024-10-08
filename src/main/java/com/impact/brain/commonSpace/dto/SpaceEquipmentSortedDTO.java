package com.impact.brain.commonSpace.dto;

import com.impact.brain.commonSpace.entity.Space;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SpaceEquipmentSortedDTO {
    private Space space;
    private ArrayList<SpaceEquipmentDTO> equipment = new ArrayList<>();
}
