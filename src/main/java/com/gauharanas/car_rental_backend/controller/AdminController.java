package com.gauharanas.car_rental_backend.controller;

import com.gauharanas.car_rental_backend.dto.AdminCarDto;
import com.gauharanas.car_rental_backend.dto.AdminLicenseDto;
import com.gauharanas.car_rental_backend.dto.BookingDto;
import com.gauharanas.car_rental_backend.dto.CarDto;
import com.gauharanas.car_rental_backend.model.Car;
import com.gauharanas.car_rental_backend.model.DriverLicense;
import com.gauharanas.car_rental_backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    // --- Car Endpoints ---
    @GetMapping("/cars/pending")
    public ResponseEntity<List<AdminCarDto>> getPendingCars() {
        return ResponseEntity.ok(adminService.getPendingCars());
    }

    @PutMapping("/car/{carNo}/approve")
    public ResponseEntity<String> approveCar(@PathVariable String carNo) {
        adminService.approveCar(carNo);
        return ResponseEntity.ok("Car approved successfully.");
    }

    @PutMapping("/car/{carNo}/reject")
    public ResponseEntity<String> rejectCar(@PathVariable String carNo) {
        adminService.rejectCar(carNo);
        return ResponseEntity.ok("Car rejected successfully.");
    }

    // --- License Endpoints ---
    @GetMapping("/licenses/pending")
    public ResponseEntity<List<AdminLicenseDto>> getPendingLicenses() {
        return ResponseEntity.ok(adminService.getPendingLicenses());
    }

    @PutMapping("/license/{licenseNumber}/approve")
    public ResponseEntity<String> approveLicense(@PathVariable String licenseNumber) {
        adminService.approveLicense(licenseNumber);
        return ResponseEntity.ok("License approved successfully.");
    }

    @PutMapping("/license/{licenseNumber}/reject")
    public ResponseEntity<String> rejectLicense(@PathVariable String licenseNumber) {
        adminService.rejectLicense(licenseNumber);
        return ResponseEntity.ok("License rejected successfully.");
    }

    //-----------Booking Endpoints-------------
    @GetMapping("/bookings/pending")
    public ResponseEntity<List<BookingDto>> getPendingBookings(){
        return ResponseEntity.ok(adminService.getPendingBookings());
    }

    @PutMapping("/booking/{bookingId}/approve")
    public ResponseEntity<String> approveLicense(@PathVariable Long bookingId){
        adminService.approveBooking(bookingId);
        return ResponseEntity.ok("Booking Confirmed Successfully");
    }

    @PutMapping("/booking/{bookingId}/reject")
    public ResponseEntity<String> rejectLicense(@PathVariable Long bookingId){
        adminService.rejectBooking(bookingId);
        return ResponseEntity.ok("Booking Rejected Successfully");
    }

}
