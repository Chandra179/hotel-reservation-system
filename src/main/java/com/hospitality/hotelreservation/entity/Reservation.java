package com.hospitality.hotelreservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @ManyToOne
    private Guest guest; // Optional, if Guest entity exists

    @ManyToOne
    private Room room;

  public Reservation(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
    this.guest = guest;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.room = room;
  }

  public double getTotalPrice() {
    // Implement logic to calculate the total price based on room rate, number of nights, etc.
    // You might need to access the associated Room entity to retrieve the room rate.
    double roomPricePerNight = room.getPrice(); // Assuming a 'room' field referencing the Room entity
    long nights = calculateNights(getCheckInDate(), getCheckOutDate());
    return roomPricePerNight * nights;
  }

  private long calculateNights(LocalDate checkIn, LocalDate checkOut) {
    // Implement logic to calculate the number of nights between check in and check out dates
    // You can use libraries like Java 8 LocalDate or a suitable date/time library
    return ChronoUnit.DAYS.between(checkIn, checkOut);
  }
}
