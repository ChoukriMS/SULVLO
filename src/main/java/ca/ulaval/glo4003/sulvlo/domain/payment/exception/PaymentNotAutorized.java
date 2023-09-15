package ca.ulaval.glo4003.sulvlo.domain.payment.exception;


import jakarta.ws.rs.NotAuthorizedException;

public class PaymentNotAutorized extends NotAuthorizedException {

  private static final String MESSAGE = "Payment not authorized";

  public PaymentNotAutorized() {
    super(MESSAGE);
  }
}
