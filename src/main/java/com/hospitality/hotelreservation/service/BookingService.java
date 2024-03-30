package com.hospitality.hotelreservation.service;

import com.hospitality.hotelreservation.entity.Guest;
import com.hospitality.hotelreservation.entity.Reservation;
import com.hospitality.hotelreservation.entity.Room;
import com.hospitality.hotelreservation.exception.BookingException;
import com.hospitality.hotelreservation.exception.PaymentException;
import com.hospitality.hotelreservation.exception.RoomException;
import com.hospitality.hotelreservation.repository.GuestRepository;
import com.hospitality.hotelreservation.repository.ReservationRepository;
import com.hospitality.hotelreservation.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {

  private final RoomRepository roomRepository;
  private final GuestRepository guestRepository;
  private final PaymentGateway paymentGateway;
  private final ReservationRepository reservationRepository;

  @Autowired
  public BookingService(RoomRepository roomRepository, GuestRepository guestRepository, PaymentGateway paymentGateway, ReservationRepository reservationRepository) {
    this.roomRepository = roomRepository;
    this.guestRepository = guestRepository;
    this.paymentGateway = paymentGateway;
    this.reservationRepository = reservationRepository;
  }

  /**
   * This method is used to make a booking for a guest in a specific room.
   *
   * @param guest The guest who is making the booking.
   * @param roomId The ID of the room that the guest wants to book.
   * @param reservation The reservation details including check-in and check-out dates.
   * @return The reservation object after it has been saved in the database.
   * @throws BookingException If the room is not available for the selected dates.
   * @throws PaymentException If the payment process fails.
   * @throws RoomException If the room with the given ID does not exist.
   */
  public Reservation makeBooking(Guest guest, Long roomId, Reservation reservation) throws BookingException, PaymentException, RoomException {
    Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomException("Room not found with ID: " + roomId));
    if (!isRoomAvailable(room, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
      throw new BookingException("Room is not available for selected dates");
    }
    if (!paymentGateway.processPayment(guest, reservation.getTotalPrice())) {
      throw new PaymentException("Payment failed");
    }
    reservation.setGuest(guest);
    reservation.setRoom(room);
    roomRepository.save(room);
    if (guest != null) {
      guestRepository.save(guest);
    }
    return reservation;
  }

  public boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
    return reservationRepository.findAllByRoom_Id(room.getId()).stream()
      .noneMatch(reservation -> (checkInDate.isBefore(reservation.getCheckOutDate()) || checkInDate.isEqual(reservation.getCheckOutDate())) &&
        (checkOutDate.isAfter(reservation.getCheckInDate()) || checkOutDate.isEqual(reservation.getCheckInDate())));
  }
}
