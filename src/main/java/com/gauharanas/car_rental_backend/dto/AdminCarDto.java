package com.gauharanas.car_rental_backend.dto;

import com.gauharanas.car_rental_backend.model.CarStatus;
import com.gauharanas.car_rental_backend.model.User;

public record AdminCarDto(
        Long id,
        String carNo,
        String brand,
        String model,
        int year,
        double pricePerDay,
        String imageUrl,
        int seatCount,
        CarStatus status,
        String ownerEmail,
        Long ownerId) {
}
