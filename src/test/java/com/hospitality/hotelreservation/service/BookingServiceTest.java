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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

  @Mock
  private RoomRepository roomRepository;

  @Mock
  private GuestRepository guestRepository;

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private PaymentGateway paymentGateway;

  @InjectMocks
  private BookingService bookingService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void roomAvailabilityShouldReturnTrueWhenNoOverlappingReservationsExist() {
    Room room = new Room();
    room.setId(1L);
    LocalDate checkInDate = LocalDate.now().plusDays(1);
    LocalDate checkOutDate = LocalDate.now().plusDays(2);

    when(reservationRepository.findAllByRoom_Id(room.getId())).thenReturn(Collections.emptyList());

    boolean result = bookingService.isRoomAvailable(room, checkInDate, checkOutDate);

    assertTrue(result);
  }

  @Test
  public void roomAvailabilityShouldReturnFalseWhenOverlappingReservationsExist() {
    Room room = new Room();
    room.setId(1L);
    LocalDate checkInDate = LocalDate.now().plusDays(1);
    LocalDate checkOutDate = LocalDate.now().plusDays(2);

    Reservation overlappingReservation = new Reservation();
    overlappingReservation.setCheckInDate(LocalDate.now());
    overlappingReservation.setCheckOutDate(LocalDate.now().plusDays(3));

    when(reservationRepository.findAllByRoom_Id(room.getId())).thenReturn(Collections.singletonList(overlappingReservation));

    boolean result = bookingService.isRoomAvailable(room, checkInDate, checkOutDate);

    assertFalse(result);
  }

  @Test
  public void makeBookingShouldThrowExceptionWhenRoomIsUnavailable() {
    Room room = new Room();
    room.setId(1L);
    when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
    Guest guest = new Guest();
    Reservation reservation = new Reservation();
    reservation.setCheckInDate(LocalDate.now().plusDays(1));
    reservation.setCheckOutDate(LocalDate.now().plusDays(2));

    when(reservationRepository.findAllByRoom_Id(room.getId())).thenReturn(Collections.singletonList(reservation));

    assertThrows(BookingException.class, () -> bookingService.makeBooking(guest, room.getId(), reservation));
  }

  @Test
  public void makeBookingShouldThrowExceptionWhenPaymentFails() throws PaymentException {
    Room room = new Room();
    room.setId(1L);
    when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
    Guest guest = new Guest();
    Reservation reservation = new Reservation();
    reservation.setCheckInDate(LocalDate.now().plusDays(1));
    reservation.setCheckOutDate(LocalDate.now().plusDays(2));
    reservation.setRoom(room);

    when(reservationRepository.findAllByRoom_Id(room.getId())).thenReturn(Collections.emptyList());
    when(paymentGateway.processPayment(guest, reservation.getTotalPrice())).thenReturn(false);

    assertThrows(PaymentException.class, () -> bookingService.makeBooking(guest, room.getId(), reservation));
  }

  @Test
  public void makeBookingShouldReturnReservationWhenRoomIsAvailableAndPaymentSucceeds() throws PaymentException, BookingException, RoomException {
    Room room = new Room();
    room.setId(1L);
    when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
    Guest guest = new Guest();
    Reservation reservation = new Reservation();
    reservation.setCheckInDate(LocalDate.now().plusDays(1));
    reservation.setCheckOutDate(LocalDate.now().plusDays(2));
    reservation.setRoom(room);

    when(reservationRepository.findAllByRoom_Id(room.getId())).thenReturn(Collections.emptyList());
    when(paymentGateway.processPayment(guest, reservation.getTotalPrice())).thenReturn(true);

    Reservation result = bookingService.makeBooking(guest, room.getId(), reservation);

    assertEquals(reservation, result);
  }
}


