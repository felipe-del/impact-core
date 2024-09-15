package com.impact.brain.commonSpace.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpaceDTO {
    private String name;
    private int spaceCode;
    private int maxPeople;
    private int spaceType;
    private int spaceStatus;
    private int buildingLocation;

    @Override
    public String toString() {
        return "SpaceDTO{" +
                ", name='" + name + '\'' +
        ", codigoDeEspacio=" + spaceCode +
                ", cantidadPersonas=" + maxPeople +
                ", tipodeEspacio=" + spaceType +
                ", statusActual=" + spaceStatus +
                ", buildingLocation=" + buildingLocation +
                '}';
    }
}