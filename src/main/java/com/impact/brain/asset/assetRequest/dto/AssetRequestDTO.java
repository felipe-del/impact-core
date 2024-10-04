package com.impact.brain.asset.assetRequest.dto;

import com.impact.brain.asset.dto.AssetDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Isaac F. B. C.
 * @since 10/3/2024 - 10:38 AM
 */
@Getter
@Setter
public class AssetRequestDTO {

    private int id;
    private int requestId;
    private int assetId;
    private int statusId;
    private String expirationDate;
    private String reason;

    @Override
    public String toString() {
        return "AssetRequestDTO{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", assetDTO=" + assetId +
                ", statusName='" + statusId + '\'' +
                ", expirationDate=" + expirationDate +
                ", reason='" + reason + '\'' +
                '}';
    }
}
