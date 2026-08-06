package com.gauharanas.car_rental_backend.repository;


import com.gauharanas.car_rental_backend.dto.BookingDto;
import com.gauharanas.car_rental_backend.model.Booking;
import com.gauharanas.car_rental_backend.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    boolean existsByCarIdAndStatusIn(Long carId, List<BookingStatus> statuses);

    List<Booking> findAllByStatus(BookingStatus status);

    List<Booking> findByCarOwnerId(Long ownerId);

    List<Booking> findByCustomerId(Long id);



    @Query("SELECT b from Booking b WHERE b.car.id = :car_id  AND " +
            "(b.status = 'CONFIRMED' ) AND" +
            "( :start_date < b.endDate AND :end_date >b.startDate)")
    List<Booking> findOverlappingBookings(Long car_id, LocalDate start_date, LocalDate end_date);

    @Query("SELECT b.car.id from Booking b WHERE b.status = 'CONFIRMED' AND " +
            "( :startDate< b.endDate AND :endDate > b.startDate) ")
    List<Long> findBookedCarsId(@Param("startDate") LocalDate startDate , @Param("endDate") LocalDate endDate);

}
