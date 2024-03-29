package com.hospitality.hotelreservation.service;

public class SimpleEmailService implements EmailService {

    private String senderEmail; // Replace with your actual sender email address

    public SimpleEmailService(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Override
    public void sendEmail(String recipientEmail, String subject, String content) {
        // Replace this logic with your actual email sending mechanism (e.g., JavaMail API, third-party library)
        System.out.println("Simulating email sent:");
        System.out.println("Recipient: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
        System.out.println("Sender: " + senderEmail);
    }
}
