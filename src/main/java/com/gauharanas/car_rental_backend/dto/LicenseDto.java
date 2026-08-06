package com.gauharanas.car_rental_backend.dto;

import java.time.LocalDate;

public record LicenseDto(String licenseNumber, String fullName, String fatherName, LocalDate validTill, String issuingState, String address,String license_img_url) {
}
