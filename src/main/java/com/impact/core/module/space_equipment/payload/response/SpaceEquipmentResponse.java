package com.impact.core.module.space_equipment.payload.response;

import com.impact.core.module.brand.payload.response.BrandResponse;
import com.impact.core.module.space.payload.response.SpaceResponse;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceEquipmentResponse {
    private int id;
    private String name;
    private BrandResponse brandResponse;
    private SpaceResponse spaceResponse;
    private int quantity;
}
