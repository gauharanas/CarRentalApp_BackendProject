package com.gauharanas.car_rental_backend.controller;

import com.gauharanas.car_rental_backend.dto.BookingDto;
import com.gauharanas.car_rental_backend.dto.CarDto;
import com.gauharanas.car_rental_backend.service.LenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lender")
public class LenderController {
    private final LenderService lenderService;


    public LenderController(LenderService lenderService) {
        this.lenderService = lenderService;
    }

    @PostMapping("/car")
    @PreAuthorize("hasAuthority('LENDER')")
    public ResponseEntity<?> addCar(@RequestBody CarDto carDto, Authentication authentication){
        lenderService.addCar(carDto, authentication.getName());
        return new ResponseEntity<>("Car added successfully and is pending verification.", HttpStatus.CREATED);
    }

    @PutMapping("/car/{carId}")
    @PreAuthorize("hasAuthority('LENDER')")
    public ResponseEntity<?> updateCar(@PathVariable  Long carId, @RequestBody CarDto carDto, Authentication authentication) {
        try {
            lenderService.updateCar(carId, carDto, authentication.getName());
            return ResponseEntity.ok("Car updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/car/{carId}")
    @PreAuthorize("hasAuthority('LENDER')")
    public ResponseEntity<?> deleteCar(@PathVariable Long carId, Authentication authentication) {
        try {
            lenderService.deleteCar(carId, authentication.getName());
            return ResponseEntity.ok("Car deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/my-cars")
    @PreAuthorize("hasAuthority('LENDER')")
    public ResponseEntity<List<CarDto>> getMyCars(Authentication authentication) {
        List<CarDto> cars = lenderService.getCarsByLender(authentication.getName());
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/bookings")
    @PreAuthorize("hasAuthority('LENDER')")
    public ResponseEntity<List<BookingDto>> getMyBookings(Authentication authentication) {
        List<BookingDto> bookings = lenderService.getBookingsByLender(authentication.getName());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/cars/available")
    @PreAuthorize("hasAuthority('LENDER')")
    public ResponseEntity<List<CarDto>> getMyAvailableCars(Authentication authentication) {
        List<CarDto> availableCars = lenderService.getAvailableCarsByLender(authentication.getName());
        return ResponseEntity.ok(availableCars);
    }
}
