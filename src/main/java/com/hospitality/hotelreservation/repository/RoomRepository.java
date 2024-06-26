package com.hospitality.hotelreservation.repository;

import com.hospitality.hotelreservation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
