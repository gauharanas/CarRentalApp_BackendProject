package com.gauharanas.car_rental_backend.dto;


import com.gauharanas.car_rental_backend.model.UserRole;

public record SignupRequest(String name, String email, String password, UserRole role) {
}
