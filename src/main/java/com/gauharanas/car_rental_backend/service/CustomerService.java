package com.gauharanas.car_rental_backend.service;

import com.gauharanas.car_rental_backend.dto.BookingDto;
import com.gauharanas.car_rental_backend.dto.BookingRequestDto;
import com.gauharanas.car_rental_backend.dto.CarDto;
import com.gauharanas.car_rental_backend.dto.LicenseDto;
import com.gauharanas.car_rental_backend.model.*;
import com.gauharanas.car_rental_backend.repository.BookingRepository;
import com.gauharanas.car_rental_backend.repository.CarRepository;
import com.gauharanas.car_rental_backend.repository.DriverLicenseRepository;
import com.gauharanas.car_rental_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final UserRepository userRepository;
    private final DriverLicenseRepository  driverLicenseRepository;
    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    DriverLicense license = new DriverLicense();

    public CustomerService(UserRepository userRepository, DriverLicenseRepository driverLicenseRepository, BookingRepository bookingRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.driverLicenseRepository = driverLicenseRepository;
        this.bookingRepository= bookingRepository;
        this.carRepository = carRepository;
    }

    public DriverLicense sumbitLicense(LicenseDto licenseDto, String customerEmail  ){
        User customer = (User)userRepository.findByEmail(customerEmail).orElseThrow(()-> new UsernameNotFoundException("Customer not found exception"));

        driverLicenseRepository.findByUserId(customer.getId()).ifPresent(license -> {
            throw new IllegalStateException("A license has already been submitted for this user.");
        });

        license.setUser(customer);
        license.setLicenseNumber(licenseDto.licenseNumber());
        license.setFullName(licenseDto.fullName());
        license.setFatherName(licenseDto.fatherName());
        license.setValidTill(licenseDto.validTill());
        license.setIssuingState(licenseDto.issuingState());
        license.setAddress(licenseDto.address());
        license.setStatus(LicenseStatus.PENDING_VERIFICATION);
        license.setLicense_img_url(licenseDto.license_img_url());

        return driverLicenseRepository.save(license);

    }

    public List<BookingDto> getAllMyBooking(String customerEmail){
        User customer = (User) userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No Customer found!!!"));

        return bookingRepository.findByCustomerId(customer.getId()).stream()
                .map(this::convertToBookingDto)
                .collect(Collectors.toList());
    }

    public Booking bookCar(BookingRequestDto bookingRequestDto, String customerEmail){
        User customer = (User) userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found!!"));

        boolean isLicenseVerified = driverLicenseRepository.existsByUserIdAndStatus(customer.getId(), LicenseStatus.VERIFIED);
        if(!isLicenseVerified){
            throw new IllegalStateException("Your Driver's License Is Not Verified");
        }

        Car car = carRepository.findByIdAndStatus(bookingRequestDto.id(), CarStatus.APPROVED)
                .orElseThrow(() -> new RuntimeException("Car is not available for booking"));

        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(bookingRequestDto.id(),bookingRequestDto.startDate(),bookingRequestDto.endDate());
        if(!overlappingBookings.isEmpty()){
            throw new IllegalStateException("Car is already booked for selected dates");
        }

        Booking booking = new Booking();
        booking.setCarNo(car.getCarNo());
        booking.setCustomer(customer);
        booking.setCar(car);
        booking.setStartDate(bookingRequestDto.startDate());
        booking.setEndDate(bookingRequestDto.endDate());
        booking.setStatus(BookingStatus.BOOKING_PENDING);

        // Calculate total price
        long days = ChronoUnit.DAYS.between(bookingRequestDto.startDate(), bookingRequestDto.endDate());
        booking.setTotalPrice(days * car.getPricePerDay());

        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId, String customerEmail) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Verify the user owns this booking
        if (!booking.getCustomer().getEmail().equals(customerEmail)) {
            throw new SecurityException("You are not authorized to cancel this booking.");
        }

        // You can add more logic here, e.g., prevent cancellation if the booking is already in progress
        if(booking.getStatus().equals(BookingStatus.REJECTED) || booking.getStatus().equals(BookingStatus.CANCELLED) || booking.getStatus().equals(BookingStatus.CONFIRMED)){
            throw new RuntimeException("Booking already cancelled or rejected or completed!");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }


    private BookingDto convertToBookingDto(Booking booking){
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
