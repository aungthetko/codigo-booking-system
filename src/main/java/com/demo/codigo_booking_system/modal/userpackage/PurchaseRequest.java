package com.demo.codigo_booking_system.modal.userpackage;

import lombok.Data;

@Data
public class PurchaseRequest {

    private String email;
    private Long packageId;
    private String packageName;

}
