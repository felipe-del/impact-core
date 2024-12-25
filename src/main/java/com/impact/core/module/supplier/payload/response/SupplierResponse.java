package com.impact.core.module.supplier.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String entityTypeName;
    private String clientContact;
    private String idNumber;
}
