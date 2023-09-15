package ca.ulaval.glo4003.sulvlo.domain.truck.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidTruckIdException extends BadRequestException {

  private static final String DESCRIPTION = "Invalid truckId";

  public InvalidTruckIdException() {
    super(DESCRIPTION);
  }
}
