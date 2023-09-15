package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidReturnLocationException extends BadRequestException {

  private static final String DESCRIPTION =
      "The bike cannot be returned. Location '%d' is not isEmpty.";

  public InvalidReturnLocationException(int returnLocation) {
    super(String.format(DESCRIPTION, returnLocation));
  }
}
