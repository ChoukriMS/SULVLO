package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidTokenException extends BadRequestException {

  private static final String MESSAGE = "Your token is not valid.";

  public InvalidTokenException() {
    super(MESSAGE);
  }

}