package com.hospitality.hotelreservation.service;

import com.hospitality.hotelreservation.entity.Guest;
import com.hospitality.hotelreservation.entity.Reservation;
import com.hospitality.hotelreservation.entity.Room;
import com.hospitality.hotelreservation.exception.BookingException;
import com.hospitality.hotelreservation.exception.PaymentException;
import com.hospitality.hotelreservation.repository.GuestRepository;
import com.hospitality.hotelreservation.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

  @Mock
  private RoomRepository roomRepository;

  @Mock
  private GuestRepository guestRepository;  // Optional, if applicable

  @Mock
  private PaymentGateway paymentGateway;

  @InjectMocks
  private BookingService bookingService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testSuccessfulBooking() throws Exception {
    // Mock room availability
    Long roomId = 1L;
    LocalDate checkInDate = LocalDate.now();
    LocalDate checkOutDate = checkInDate.plusDays(1);
    double roomPricePerNight = 100.0;

    Room room = new Room();
    room.setId(roomId);
    room.setPrice(roomPricePerNight);

    when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));  // Replace with an actual Room object if needed
    when(roomRepository.isAvailable(roomId, checkInDate, checkOutDate)).thenReturn(true);

    // Mock successful payment
    Guest guest = new Guest("John Doe", "johndoe@example.com");
    long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    double totalPrice = roomPricePerNight * nights;

    when(paymentGateway.processPayment(guest, totalPrice)).thenReturn(true);

    // Create reservation
    Reservation reservation = new Reservation(guest, room, checkInDate, checkOutDate);

    // Make booking
    Reservation madeReservation = bookingService.makeBooking(guest, roomId, reservation);

    // Assertions
    assertNotNull(madeReservation, "Reservation should not be null");
    assertEquals(roomId, madeReservation.getRoom().getId());
    assertEquals(totalPrice, madeReservation.getTotalPrice(), 0.01);  // Allow for slight double precision difference
  }

  @Test
  public void testRoomUnavailable() {
    // Mock room unavailability
    Long roomId = 1L;
    LocalDate checkInDate = LocalDate.now();
    LocalDate checkOutDate = checkInDate.plusDays(1);

    when(roomRepository.findById(roomId)).thenReturn(Optional.of(new Room()));
    when(roomRepository.isAvailable(roomId, checkInDate, checkOutDate)).thenReturn(false);

    // Create reservation
    Reservation reservation = new Reservation();

    // Assert BookingException is thrown
    assertThrows(BookingException.class, () -> bookingService.makeBooking(null, 1L, reservation));
  }

  @Test
  public void testPaymentFailure() throws Exception {
    // Mock room availability
    Long roomId = 1L;
    LocalDate checkInDate = LocalDate.now();
    LocalDate checkOutDate = checkInDate.plusDays(1);
    double roomPricePerNight = 100.0;

    Room room = new Room();
    room.setId(roomId);
    room.setPrice(roomPricePerNight);

    when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));  // Replace with an actual Room object if needed
    when(roomRepository.isAvailable(roomId, checkInDate, checkOutDate)).thenReturn(true);

    // Mock successful payment
    Guest guest = new Guest("John Doe", "johndoe@example.com");
    long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    double totalPrice = roomPricePerNight * nights;

    when(paymentGateway.processPayment(guest, totalPrice)).thenReturn(false);

    // Create reservation
    Reservation reservation = new Reservation(guest, room, checkInDate, checkOutDate);

    // Make booking - expect PaymentException
    assertThrows(PaymentException.class, () -> bookingService.makeBooking(guest, roomId, reservation));
  }
}


