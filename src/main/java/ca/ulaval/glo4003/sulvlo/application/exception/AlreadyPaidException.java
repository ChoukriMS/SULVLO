package ca.ulaval.glo4003.sulvlo.application.exception;

import ca.ulaval.glo4003.sulvlo.infrastructure.exception.ConflictException;

public class AlreadyPaidException extends ConflictException {

  private static final String PAYER_ALREADY_PAID = "You already paid what you owed.";

  public AlreadyPaidException() {
    super(PAYER_ALREADY_PAID);
  }

}
