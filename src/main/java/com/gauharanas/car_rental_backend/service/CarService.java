package com.gauharanas.car_rental_backend.service;

import com.gauharanas.car_rental_backend.dto.AdminCarDto;
import com.gauharanas.car_rental_backend.dto.CarDto;
import com.gauharanas.car_rental_backend.model.BookingStatus;
import com.gauharanas.car_rental_backend.model.Car;
import com.gauharanas.car_rental_backend.model.CarStatus;
import com.gauharanas.car_rental_backend.repository.BookingRepository;
import com.gauharanas.car_rental_backend.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    public CarService(CarRepository carRepository, BookingRepository bookingRepository) {

        this.carRepository = carRepository;
        this.bookingRepository= bookingRepository;
    }

    public List<AdminCarDto> getAllApprovedCars(){
        return carRepository.findAllByStatus(CarStatus.APPROVED).stream()
                .map(this::convertToAdminCarDto)
                .collect(Collectors.toList());
    }

    public AdminCarDto getCarById(Long carId){
        Car car = carRepository.findByIdAndStatus(carId, CarStatus.APPROVED).orElseThrow(()-> new RuntimeException("Car not approved yet!!"));
        return convertToAdminCarDto(car);
    }

    public List<AdminCarDto> searchCars(LocalDate startDate, LocalDate endDate){

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        List<Long> bookedCarIds = bookingRepository.findBookedCarsId(startDate,endDate);

        List<Car> allApprovedCars = carRepository.findAllByStatus(CarStatus.APPROVED);

        return allApprovedCars.stream()
                .filter(car -> !bookedCarIds.contains(car.getId()))
                .map(this:: convertToAdminCarDto)
                .collect(Collectors.toList());


    }

    private AdminCarDto convertToAdminCarDto(Car car){
        return new AdminCarDto(
                car.getId(),
                car.getCarNo(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPricePerDay(),
                car.getImageUrl(),
                car.getSeatCount(),
                car.getStatus(),
                car.getOwner().getEmail(),
                car.getOwner().getId());
    }
}
