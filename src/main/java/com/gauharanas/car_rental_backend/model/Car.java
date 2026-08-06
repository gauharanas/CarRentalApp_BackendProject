package com.gauharanas.car_rental_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="car_no",nullable = false,unique = true)
    private String carNo;
    @Column(name="car_brand",nullable = false)
    private String brand;
    @Column(name="car_model",nullable = false)
    private String model;
    private int year;
    @Column(name="price_per_day",nullable = false)
    private double pricePerDay;
    @Column(name="car_image_url",nullable = false)
    private String imageUrl;
    @Column(name="seat_count",nullable = false)
    private int seatCount;
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

}
