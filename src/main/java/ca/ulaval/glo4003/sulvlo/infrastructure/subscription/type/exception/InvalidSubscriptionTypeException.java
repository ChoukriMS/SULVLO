package ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidSubscriptionTypeException extends BadRequestException {

  private static final String MESSAGE = "There is no subscription type with that name, please try again!";

  public InvalidSubscriptionTypeException() {
    super(MESSAGE);
  }
}


