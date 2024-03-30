package com.hospitality.hotelreservation.repository;

import com.hospitality.hotelreservation.entity.Reservation;
import com.hospitality.hotelreservation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  List<Reservation> findAllByRoom_Id(Long roomId);
}
