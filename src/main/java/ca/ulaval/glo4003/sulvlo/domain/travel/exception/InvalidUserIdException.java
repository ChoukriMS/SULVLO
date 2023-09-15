package ca.ulaval.glo4003.sulvlo.domain.travel.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidUserIdException extends BadRequestException {

  private static final String DESCRIPTION = "You have entered an invalid user ID!";

  public InvalidUserIdException() {
    super(DESCRIPTION);
  }
}
