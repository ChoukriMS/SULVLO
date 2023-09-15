package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.BadRequestException;

public class TokenExpiredException extends BadRequestException {

  private static final String MESSAGE = "Your token has expired.";

  public TokenExpiredException() {
    super(MESSAGE);
  }

}
