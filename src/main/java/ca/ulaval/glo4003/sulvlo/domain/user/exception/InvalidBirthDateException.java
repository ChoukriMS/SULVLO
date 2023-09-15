package ca.ulaval.glo4003.sulvlo.domain.user.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidBirthDateException extends BadRequestException {

  private static final String WRONG_DATE = "The given date was not valid.";

  public InvalidBirthDateException() {
    super(WRONG_DATE);
  }
}
