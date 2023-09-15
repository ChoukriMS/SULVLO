package ca.ulaval.glo4003.sulvlo.application.service.station.exception;

import jakarta.ws.rs.BadRequestException;

public class UnlockBikeException extends BadRequestException {

  private static final String DESCRIPTION = "Must return bike before unlock another one";

  public UnlockBikeException() {
    super(DESCRIPTION);
  }
}
