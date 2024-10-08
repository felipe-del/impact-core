package com.impact.brain.commonSpace.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpaceEquipmentDTO {
    private Integer id;
    private String name;
    private Integer brandId;
    private Integer spaceId;
    private Integer quantity;
}
