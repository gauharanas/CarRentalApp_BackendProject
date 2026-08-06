package com.gauharanas.car_rental_backend.repository;

import com.gauharanas.car_rental_backend.model.DriverLicense;
import com.gauharanas.car_rental_backend.model.LicenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverLicenseRepository extends JpaRepository<DriverLicense,Long> {
    Optional<DriverLicense> findByUserId(Long userId);
    List<DriverLicense> findAllByStatus(LicenseStatus status);
    Optional<DriverLicense> findByLicenseNumber(String licenseNumber);
    boolean existsByUserIdAndStatus(Long userId, LicenseStatus status);
}
