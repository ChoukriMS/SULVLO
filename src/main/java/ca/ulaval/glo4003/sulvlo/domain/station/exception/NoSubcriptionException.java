package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.BadRequestException;

public class NoSubcriptionException extends BadRequestException {

  public static final String MESSAGE = "You have no subscription, you can not unlock a bike.";

  public NoSubcriptionException() {
    super(MESSAGE);
  }

}
