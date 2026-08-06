package com.gauharanas.car_rental_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="Driver_License_Details")
public class DriverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(name="full_name",nullable = false)
    private String fullName;

    @Column(name="father_name",nullable = false)
    private String fatherName;

    @Column(name = "valid_till", nullable = false)
    private LocalDate validTill;

    @Column(name = "issuing_state", nullable = false)
    private String issuingState;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LicenseStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name="license_img_url", nullable = false)
    private String license_img_url;

}
