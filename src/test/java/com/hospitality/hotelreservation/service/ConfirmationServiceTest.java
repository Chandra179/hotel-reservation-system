package com.hospitality.hotelreservation.service;

import com.hospitality.hotelreservation.entity.Guest;
import com.hospitality.hotelreservation.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class ConfirmationServiceTest {

  @Mock
  private EmailService emailService;

  @InjectMocks
  private ConfirmationService confirmationService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void sendConfirmationEmail_callsEmailServiceCorrectly() {
    // Set up test data
    Reservation reservation = new Reservation(new Guest("john.doe@example.com", "John Doe"), null, null, null); // Add other reservation details

    // Call the method under test
    confirmationService.sendConfirmationEmail(reservation);

    // Verify interactions with mocks
    verify(emailService).sendEmail(reservation.getGuest().getEmail(), "Hotel Reservation Confirmation", "");
  }
}
