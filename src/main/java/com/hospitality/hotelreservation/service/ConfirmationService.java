package com.hospitality.hotelreservation.service;

import com.hospitality.hotelreservation.entity.Reservation;

public class ConfirmationService {

    private EmailService emailService;  // Replace with your email sending logic

    public ConfirmationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendConfirmationEmail(Reservation reservation) {
        // Prepare confirmation email content with reservation details (hotel, room, guest, dates)
        String emailContent = prepareConfirmationEmailContent(reservation);

        // Send email using the EmailService  (replace with your actual email sending logic)
        emailService.sendEmail(reservation.getGuest().getEmail(), "Hotel Reservation Confirmation", emailContent);
    }

    private String prepareConfirmationEmailContent(Reservation reservation) {
        // Implement logic to build the email content with reservation details
        StringBuilder content = new StringBuilder();
        // ... (add reservation details to the content)
        return content.toString();
    }
}
