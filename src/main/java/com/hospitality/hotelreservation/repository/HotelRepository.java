package com.hospitality.hotelreservation.repository;

import com.hospitality.hotelreservation.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Optional method for searching based on availability (implementation depends on your database schema)
    List<Hotel> findAvailableHotels(String location, LocalDate checkInDate, LocalDate checkOutDate);
}
