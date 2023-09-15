package ca.ulaval.glo4003.sulvlo.domain.payment.exception;

import jakarta.ws.rs.NotAuthorizedException;

public class AlreadyPaidException extends NotAuthorizedException {

  private static final String MESSAGE = "Payment has already been made.";

  public AlreadyPaidException() {
    super(MESSAGE);
  }
}
