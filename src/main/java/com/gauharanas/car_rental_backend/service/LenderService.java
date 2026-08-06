package com.gauharanas.car_rental_backend.service;

import com.gauharanas.car_rental_backend.dto.BookingDto;
import com.gauharanas.car_rental_backend.dto.CarDto;
import com.gauharanas.car_rental_backend.model.*;
import com.gauharanas.car_rental_backend.repository.BookingRepository;
import com.gauharanas.car_rental_backend.repository.CarRepository;
import com.gauharanas.car_rental_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LenderService {
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;

    public LenderService(UserRepository userRepository, CarRepository carRepository,BookingRepository bookingRepository ) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
    }

    public Car addCar(CarDto carDto, String ownerEmail){
        User owner = userRepository.findByEmail(ownerEmail).orElseThrow(()->new UsernameNotFoundException("User not Found!"));
        Car car = new Car();
        car.setCarNo(carDto.carNo());
        car.setModel(carDto.model());
        car.setBrand(carDto.brand());
        car.setYear(carDto.year());
        car.setPricePerDay(carDto.pricePerDay());
        car.setImageUrl(carDto.imageUrl());
        car.setOwner(owner);
        car.setStatus(CarStatus.VERIFICATION_PENDING);
        car.setSeatCount(carDto.seatCount());
        return carRepository.save(car);
    }

    // Add the updateCar method
    public Car updateCar(Long carId, CarDto carDto, String ownerEmail) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Ownership Check
        if (!car.getOwner().getEmail().equals(ownerEmail)) {
            throw new SecurityException("You do not own this car.");
        }

        // Rental Check
        if (isCarRented(carId)) {
            throw new IllegalStateException("Cannot update a car that is currently rented.");
        }
        car.setCarNo(carDto.carNo() == null ||  carDto.carNo().isEmpty() ?car.getCarNo(): carDto.carNo()  );
        car.setBrand(carDto.brand() == null || carDto.brand().isEmpty() ? car.getBrand() : carDto.brand());
        car.setModel(carDto.model() == null || carDto.model().isEmpty() ? car.getModel() : carDto.model());
        car.setYear(carDto.year() <=0  ? car. getYear() : carDto.year());
        car.setPricePerDay( carDto.pricePerDay() <=0 ? car.getPricePerDay() : carDto.pricePerDay());
        car.setImageUrl(carDto.imageUrl() == null || carDto.imageUrl().isEmpty() ? car.getImageUrl() : carDto.imageUrl());
        car.setSeatCount( carDto.seatCount() <=0 ? car.getSeatCount() : carDto.seatCount());

        return carRepository.save(car);
    }

    // Add the deleteCar method
    public void deleteCar(Long carId, String ownerEmail) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Ownership Check
        if (!car.getOwner().getEmail().equals(ownerEmail)) {
            throw new SecurityException("You do not own this car.");
        }

        // Rental Check
        if (isCarRented(carId)) {
            throw new IllegalStateException("Cannot delete a car that is currently rented.");
        }
        carRepository.deleteById(carId);
    }

    // Helper method to check if a car is rented
    private boolean isCarRented(Long carId) {
        return bookingRepository.existsByCarIdAndStatusIn(carId, List.of(BookingStatus.CONFIRMED));
    }

    // 1. Get all cars for the logged-in lender
    public List<CarDto> getCarsByLender(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return carRepository.findByOwnerId(owner.getId()).stream()
                .map(car -> new CarDto(car.getCarNo(),car.getBrand(), car.getModel(), car.getPricePerDay(), car.getImageUrl(), car.getYear(), car.getSeatCount()))
                .collect(Collectors.toList());
    }

    // 2. Get all bookings for the lender's cars
    public List<BookingDto> getBookingsByLender(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return bookingRepository.findByCarOwnerId(owner.getId()).stream()
                .map(this::convertToBookingDto)
                .collect(Collectors.toList());
    }

    // 3. Get available cars for the lender
    public List<CarDto> getAvailableCarsByLender(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Car> lenderCars = carRepository.findByOwnerId(owner.getId());

        return lenderCars.stream()
                .filter(car -> car.getStatus() == CarStatus.APPROVED && !isCarRented(car.getId()))
                .map(car -> new CarDto(car.getCarNo(),car.getBrand(), car.getModel(), car.getPricePerDay(), car.getImageUrl(), car.getYear(), car.getSeatCount()))
                .collect(Collectors.toList());
    }

    // Helper method to convert Booking entity to BookingDto
    private BookingDto convertToBookingDto(Booking booking) {
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
