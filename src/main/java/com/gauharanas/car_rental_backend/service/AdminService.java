package com.gauharanas.car_rental_backend.service;

import com.gauharanas.car_rental_backend.dto.AdminCarDto;
import com.gauharanas.car_rental_backend.dto.AdminLicenseDto;
import com.gauharanas.car_rental_backend.dto.BookingDto;
import com.gauharanas.car_rental_backend.dto.CarDto;
import com.gauharanas.car_rental_backend.model.*;
import com.gauharanas.car_rental_backend.repository.BookingRepository;
import com.gauharanas.car_rental_backend.repository.CarRepository;
import com.gauharanas.car_rental_backend.repository.DriverLicenseRepository;
import com.gauharanas.car_rental_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final CarRepository carRepository;
    private final DriverLicenseRepository driverLicenseRepository;
    private final BookingRepository bookingRepository;

    public AdminService(CarRepository carRepository, DriverLicenseRepository driverLicenseRepository,BookingRepository bookingRepository) {
        this.carRepository = carRepository;
        this.driverLicenseRepository = driverLicenseRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<AdminCarDto> getPendingCars(){
        return carRepository.findAllByStatus(CarStatus.VERIFICATION_PENDING).stream()
                .map(this::convertToAdminCarDto)
                .collect(Collectors.toList());
    }

    public Car approveCar(String carNo){
        Car car = (Car)carRepository.findByCarNo(carNo).orElseThrow(()-> new RuntimeException("Car Not Found Exception!!"));
        car.setStatus(CarStatus.APPROVED);
        return carRepository.save(car);
    }

    public Car rejectCar(String carNo){
        Car car = (Car)carRepository.findByCarNo(carNo).orElseThrow(()-> new RuntimeException("Car Not Found Exception!!!"));
        car.setStatus(CarStatus.REJECTED);
        return carRepository.save(car);
    }

    public List<AdminLicenseDto> getPendingLicenses(){
        return driverLicenseRepository.findAllByStatus(LicenseStatus.PENDING_VERIFICATION).stream()
                .map(this::convertToAdminLicenseDto)
                .collect(Collectors.toList());
    }

    public DriverLicense approveLicense(String licenseNo){
        DriverLicense driverLicense = (DriverLicense) driverLicenseRepository.findByLicenseNumber(licenseNo).orElseThrow(()-> new RuntimeException("License Not found"));
        driverLicense.setStatus(LicenseStatus.VERIFIED);
        return driverLicenseRepository.save(driverLicense);
    }

    public DriverLicense rejectLicense(String licenseNo){
        DriverLicense driverLicense =  (DriverLicense) driverLicenseRepository.findByLicenseNumber(licenseNo).orElseThrow(()-> new RuntimeException("License Not found"));
        driverLicense.setStatus(LicenseStatus.REJECTED);
        return driverLicenseRepository.save(driverLicense);
    }

    public List<BookingDto> getPendingBookings(){
        return bookingRepository.findAllByStatus(BookingStatus.BOOKING_PENDING).stream()
                .map(this::convertToBookingDto)
                .collect(Collectors.toList());
    }

    public Booking approveBooking(Long BookingId){
        Booking booking =  bookingRepository.findById(BookingId).orElseThrow(()-> new RuntimeException("Booking Not Found Exception"));
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking rejectBooking(Long BookingId){
        Booking booking =  bookingRepository.findById(BookingId).orElseThrow(()-> new RuntimeException("Booking Not Found Exception"));
        booking.setStatus(BookingStatus.REJECTED);
        return bookingRepository.save(booking);
    }


    public AdminCarDto convertToAdminCarDto(Car car){
        return new AdminCarDto(car.getId(),
                car.getCarNo(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPricePerDay(),
                car.getImageUrl(),
                car.getSeatCount(),
                car.getStatus(),
                car.getOwner().getEmail(),
                car.getOwner().getId()
        );
    }

    public AdminLicenseDto convertToAdminLicenseDto(DriverLicense license){
        return new AdminLicenseDto(
                license.getId(),
                license.getLicenseNumber(),
                license.getFullName(),
                license.getFatherName(),
                license.getValidTill(),
                license.getIssuingState(),
                license.getAddress(),
                license.getStatus(),
                license.getUser().getId(),
                license.getLicense_img_url()
        );
    }

    public BookingDto convertToBookingDto(Booking booking){
        return new BookingDto(
                booking.getId(),
                booking.getCarNo(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getTotalPrice(),
                booking.getStatus(),
                booking.getCustomer().getName(),
                booking.getCar().getBrand(),
                booking.getCar().getModel()
        );
    }
}
