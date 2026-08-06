package com.gauharanas.car_rental_backend.controller;

import com.gauharanas.car_rental_backend.dto.BookingDto;
import com.gauharanas.car_rental_backend.dto.BookingRequestDto;
import com.gauharanas.car_rental_backend.dto.CarDto;
import com.gauharanas.car_rental_backend.dto.LicenseDto;
import com.gauharanas.car_rental_backend.service.CustomerService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/license")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> submitLicense(@RequestBody LicenseDto licenseDto, Authentication authentication){
        try {
            customerService.sumbitLicense(licenseDto, authentication.getName());
            return new ResponseEntity<>("License SUbmitted successfully. Awaiting verification.", HttpStatus.CREATED);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<BookingDto>> getAllMyBooking(Authentication authentication){

            List<BookingDto> bookings = customerService.getAllMyBooking(authentication.getName());
            return ResponseEntity.ok(bookings);

    }

    @PostMapping("/book")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequestDto bookingRequestDto, Authentication authentication) {
        try {
            customerService.bookCar(bookingRequestDto, authentication.getName());
            return new ResponseEntity<>("Booking request sent successfully. Awaiting admin approval.", HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/booking/{bookingId}/cancel")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId, Authentication authentication) {
        try {
            customerService.cancelBooking(bookingId, authentication.getName());
            return ResponseEntity.ok("Booking cancelled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
