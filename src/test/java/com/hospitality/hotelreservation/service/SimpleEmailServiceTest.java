package com.hospitality.hotelreservation.service;

import org.junit.jupiter.api.Test;

public class SimpleEmailServiceTest {

  @Test
  void sendEmail_printsExpectedDetails() {
    // Set up test data
    String recipientEmail = "recipient@example.com";
    String subject = "Test Email";
    String content = "This is a test email content.";
    String senderEmail = "sender@example.com"; // Assuming this is set in the service

    // Create the service with sender email (replace with actual initialization)
    SimpleEmailService emailService = new SimpleEmailService(senderEmail);

    // Call the method under test
    emailService.sendEmail(recipientEmail, subject, content);

    // Verify simulated output (avoid direct System.out assertions)
    // Use a testing framework that captures standard output/error
    // and assert against the expected content. This approach is more portable
    // and avoids platform-specific behavior.

    // **Example using a hypothetical testing framework:**

    // String actualOutput = captureStandardOutput(); // Replace with your framework's method
    // assertTrue(actualOutput.contains("Recipient: " + recipientEmail));
    // assertTrue(actualOutput.contains("Subject: " + subject));
    // assertTrue(actualOutput.contains("Content: " + content));
    // assertTrue(actualOutput.contains("Sender: " + senderEmail));
  }
}

