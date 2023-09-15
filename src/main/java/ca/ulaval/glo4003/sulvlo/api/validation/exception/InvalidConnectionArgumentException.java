package ca.ulaval.glo4003.sulvlo.api.validation.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidConnectionArgumentException extends BadRequestException {

  private static final String DESCRIPTION = "you have entered one or more invalid field, please try again!";

  public InvalidConnectionArgumentException() {
    super(DESCRIPTION);
  }
}
