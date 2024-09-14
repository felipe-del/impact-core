package com.impact.brain.commonSpace.dto;

import com.impact.brain.commonSpace.entity.Building;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class BuildingDTO {
    Building building;
    ArrayList<BuildingLocationDTO> locations = new ArrayList<>();
}
