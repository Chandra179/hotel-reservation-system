package com.hospitality.hotelreservation.service;

public interface EmailService {

    void sendEmail(String recipientEmail, String subject, String content);
}
