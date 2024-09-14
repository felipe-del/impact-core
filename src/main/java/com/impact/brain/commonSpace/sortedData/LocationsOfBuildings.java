package com.impact.brain.commonSpace.sortedData;

import com.impact.brain.commonSpace.dto.BuildingLocationDTO;
import com.impact.brain.commonSpace.entity.Building;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class LocationsOfBuildings {
    Building building;
    ArrayList<BuildingLocationDTO> locations = new ArrayList<>();
}
