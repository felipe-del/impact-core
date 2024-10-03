package com.impact.brain.commonSpace.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
public class SpaceDTO {
    private String name;
    private int spaceCode;
    private int maxPeople;
    private int spaceStatus;
    private int buildingLocation;
    private LocalTime openTime;
    private LocalTime closeTime;

    @Override
    public String toString() {
        return "SpaceDTO{" +
                ", name='" + name + '\'' +
        ", codigoDeEspacio=" + spaceCode +
                ", cantidadPersonas=" + maxPeople +
                ", statusActual=" + spaceStatus +
                ", buildingLocation=" + buildingLocation +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}