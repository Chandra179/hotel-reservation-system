package com.hospitality.hotelreservation.service;

import com.hospitality.hotelreservation.entity.Guest;
import com.hospitality.hotelreservation.entity.Reservation;
import com.hospitality.hotelreservation.entity.Room;
import com.hospitality.hotelreservation.exception.BookingException;
import com.hospitality.hotelreservation.exception.PaymentException;
import com.hospitality.hotelreservation.exception.RoomException;
import com.hospitality.hotelreservation.repository.GuestRepository;
import com.hospitality.hotelreservation.repository.RoomRepository;

public class BookingService {

    private RoomRepository roomRepository;  // Replace with your actual Room data access
    private GuestRepository guestRepository;  // Optional, if Guest entity exists
    private PaymentGateway paymentGateway;  // Replace with your payment processing integration

    public BookingService(RoomRepository roomRepository, GuestRepository guestRepository, PaymentGateway paymentGateway) {
      this.roomRepository = roomRepository;
      this.guestRepository = guestRepository;
      this.paymentGateway = paymentGateway;
    }

    public Reservation makeBooking(Guest guest, Long roomId, Reservation reservation) throws BookingException, PaymentException, RoomException {
      // Fetch the room object by ID from the database
      Room room = roomRepository.findById(roomId).orElseThrow(()-> new RoomException("Room not found with ID: " + roomId));

      // Check room availability for reservation dates
      if (!roomRepository.isAvailable(room.getId(), reservation.getCheckInDate(), reservation.getCheckOutDate())) {
        throw new BookingException("Room is not available for selected dates");
      }

        // Process payment (replace with actual payment logic using PaymentGateway)
      if (!paymentGateway.processPayment(guest, reservation.getTotalPrice())) {
        throw new PaymentException("Payment failed");
      }

        // Save reservation details (replace with actual data persistence logic)
      reservation.setGuest(guest);
      reservation.setRoom(room);
      roomRepository.save(room);  // Update room availability (implementation depends on your approach)
      if (guest != null) {
        guestRepository.save(guest);  // Save guest information if applicable
      }
      return reservation;
    }
}
