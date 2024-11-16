package com.demo.codigo_booking_system.service.packages;

import com.demo.codigo_booking_system.modal.userpackage.Packages;
import com.demo.codigo_booking_system.modal.userpackage.PurchaseRequest;

public interface PackageService {

    Packages createPackages(Packages packages);

    Packages purchasePackages(PurchaseRequest purchaseRequest);

}
