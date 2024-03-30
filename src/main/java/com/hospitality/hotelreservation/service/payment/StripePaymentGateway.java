package com.hospitality.hotelreservation.service.payment;

import com.hospitality.hotelreservation.entity.Guest;
import com.hospitality.hotelreservation.exception.PaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements PaymentGateway {

  @Override
  public boolean processPayment(Guest guest, double amount) throws PaymentException {
    return true;
  }
}
