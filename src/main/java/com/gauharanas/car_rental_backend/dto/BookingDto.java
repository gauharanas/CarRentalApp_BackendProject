package com.gauharanas.car_rental_backend.dto;


import com.gauharanas.car_rental_backend.model.BookingStatus;
import java.time.LocalDate;

public record BookingDto(
        Long id,
        String carNo,
        LocalDate startDate,
        LocalDate endDate,
        double totalPrice,
        BookingStatus status,
        String customerName,
        String carBrand,
        String carModel
) {
}