package ca.ulaval.glo4003.sulvlo.infrastructure.travel.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidTravelIdException extends BadRequestException {

  private static final String EXCEPTION_MESSAGE = "There is no travel with that ID, please try again!";

  public InvalidTravelIdException() {
    super(EXCEPTION_MESSAGE);
  }
}