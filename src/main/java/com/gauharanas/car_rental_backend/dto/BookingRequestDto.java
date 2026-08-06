package com.gauharanas.car_rental_backend.dto;

import java.time.LocalDate;

public record BookingRequestDto(Long id,String carNo, LocalDate startDate, LocalDate endDate) {
}
