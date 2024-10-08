package com.impact.brain.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * @author Isaac F. B. C.
 * @since 10/4/2024 - 7:21 PM
 */
@Getter
@Setter
public class ProductRequestDTO {
    private String categoryName;
    private int count;
    private int requestId;
    private int productId;
    private int statusId;
    private String reason;

    @Override
    public String toString() {
        return "ProductRequestDTO{" +
                "categoryName=" + categoryName +
                ", requestId=" + requestId +
                ", productId=" + productId +
                ", statusId=" + statusId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
