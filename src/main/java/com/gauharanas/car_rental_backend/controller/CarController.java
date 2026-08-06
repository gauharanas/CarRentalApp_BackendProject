package com.gauharanas.car_rental_backend.controller;

import com.gauharanas.car_rental_backend.dto.AdminCarDto;
import com.gauharanas.car_rental_backend.dto.CarDto;
import com.gauharanas.car_rental_backend.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public ResponseEntity<List<AdminCarDto>> getAllApprovedCars(){
        return ResponseEntity.ok(carService.getAllApprovedCars());
    }

    @GetMapping("/{carId}")
    public ResponseEntity<AdminCarDto> getCarById(@PathVariable Long carId){
        try {
            return ResponseEntity.ok(carService.getCarById(carId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdminCarDto>> searchCars(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        List<AdminCarDto> availableCars = carService.searchCars(startDate,endDate);
        try {
            return ResponseEntity.ok(carService.searchCars(startDate,endDate));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
