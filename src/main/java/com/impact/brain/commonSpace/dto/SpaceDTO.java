package com.impact.brain.commonSpace.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpaceDTO {
    @Getter
    private Integer id;
    private String name;
    private Integer spaceCode;
    private String locationName;
    private Integer maxPeople;
    private String typeName;
    private String statusName;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpaceCode() {
        return spaceCode;
    }

    public void setSpaceCode(Integer spaceCode) {
        this.spaceCode = spaceCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;

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
