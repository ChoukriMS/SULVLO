package ca.ulaval.glo4003.sulvlo.infrastructure.payer.exception;

import ca.ulaval.glo4003.sulvlo.infrastructure.exception.ConflictException;

public class PayerAlreadyExistsException extends ConflictException {

  static final String message = "The payer is already exist";

  public PayerAlreadyExistsException() {
    super(message);
  }

}
