package com.gauharanas.car_rental_backend.dto;

public record CarDto(String carNo, String brand , String model, double pricePerDay, String imageUrl, int year, int seatCount) {
}
