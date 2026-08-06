package com.gauharanas.car_rental_backend.repository;

import com.gauharanas.car_rental_backend.model.BookingStatus;
import com.gauharanas.car_rental_backend.model.Car;
import com.gauharanas.car_rental_backend.model.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByOwnerId(Long ownerId);
    List<Car> findAllByStatus(CarStatus status);
    Optional<Car> findByCarNo(String carNo);
    Optional<Car> findByIdAndStatus(Long id,CarStatus status);
}
