package com.demo.codigo_booking_system.repo;

import com.demo.codigo_booking_system.modal.userpackage.Packages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Packages, Long> {

}
