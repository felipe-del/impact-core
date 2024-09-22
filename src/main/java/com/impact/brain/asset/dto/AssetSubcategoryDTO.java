package com.impact.brain.asset.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/21/2024 - 7:45 PM
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AssetSubcategoryDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;

    @Override
    public String toString() {
        return "AssetSubcategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}