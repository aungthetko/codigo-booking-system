package com.demo.codigo_booking_system.service.packages;

import com.demo.codigo_booking_system.exception.custom.NotEligibleException;
import com.demo.codigo_booking_system.exception.custom.UserNotFoundException;
import com.demo.codigo_booking_system.modal.user.User;
import com.demo.codigo_booking_system.modal.userpackage.Packages;
import com.demo.codigo_booking_system.modal.userpackage.PurchaseRequest;
import com.demo.codigo_booking_system.repo.PackageRepository;
import com.demo.codigo_booking_system.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private final UserRepository userRepository;

    @Override
    public Packages createPackages(Packages packages) {
        return packageRepository.save(packages);
    }

    @Override
    @Transactional
    public Packages purchasePackages(PurchaseRequest purchaseRequest) {
        User user = userRepository.findUserByEmail(purchaseRequest.getEmail())
                .stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException("User was not found"));

        Packages packages = packageRepository.findById(purchaseRequest.getPackageId())
                .stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Package was not found"));

        user.getPackages().add(packages);
        packages.setUser(user);
        userRepository.save(user);
        packageRepository.save(packages);
        return packages;
    }
}
