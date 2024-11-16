package com.demo.codigo_booking_system.controller;

import com.demo.codigo_booking_system.modal.userpackage.Packages;
import com.demo.codigo_booking_system.modal.userpackage.PurchaseRequest;
import com.demo.codigo_booking_system.service.packages.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @PostMapping("/create")
    public ResponseEntity<Packages> createPackage(@RequestBody Packages packages){
        Packages newPackage = packageService.createPackages(packages);
        return new ResponseEntity<>(newPackage, HttpStatus.CREATED);
    }

    @PostMapping("/purchase")
    public ResponseEntity<Packages> purchasePackage(@RequestBody PurchaseRequest purchaseRequest){
        Packages packages = packageService.purchasePackages(purchaseRequest);
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }
}
