package com.hospitality.hotelreservation.service.payment;

import com.hospitality.hotelreservation.entity.Guest;
import com.hospitality.hotelreservation.exception.PaymentException;

public interface PaymentGateway {

    /**
     * Processes a payment for a given amount.
     *
     * @param guest The guest making the payment.
     * @param amount The total amount to be charged.
     * @return true if the payment is successful, false otherwise.
     * @throws PaymentException If an error occurs during processing.
     */
    boolean processPayment(Guest guest, double amount) throws PaymentException;
}
