package ca.ulaval.glo4003.sulvlo.domain.user.exception;

import jakarta.ws.rs.BadRequestException;

public class UniqueCodeTokenException extends BadRequestException {

  private static final String DESCRIPTION = "The unique token that you provided has expired!";

  public UniqueCodeTokenException() {
    super(DESCRIPTION);
  }
}
