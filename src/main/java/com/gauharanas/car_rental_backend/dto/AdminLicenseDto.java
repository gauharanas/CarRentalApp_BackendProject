package com.gauharanas.car_rental_backend.dto;

import com.gauharanas.car_rental_backend.model.LicenseStatus;

import java.time.LocalDate;

public record AdminLicenseDto(
        Long id,
        String licenseNumber,
        String fullName,
        String fatherName,
        LocalDate validTill,
        String issuingState,
        String address,
        LicenseStatus status,
        Long userId,
        String license_image_url
) {
}
