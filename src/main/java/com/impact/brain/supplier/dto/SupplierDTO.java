package com.impact.brain.supplier.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/20/2024 - 6:59 PM
 */
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDTO {

    private Integer id;

    private String name;

    private String phone;

    private String email;

    private String address;

    private Integer entityTypeId;

    private String clientContact;

    private String idNumber;

}
