package ca.ulaval.glo4003.sulvlo.infrastructure.payer.exception;

import jakarta.ws.rs.NotFoundException;

public class PayerNotFoundException extends NotFoundException {

  static final String message = "The payer don't exist";

  public PayerNotFoundException() {
    super(message);
  }

}
