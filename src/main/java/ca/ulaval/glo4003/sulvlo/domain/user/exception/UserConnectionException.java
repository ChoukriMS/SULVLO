package ca.ulaval.glo4003.sulvlo.domain.user.exception;

import jakarta.ws.rs.BadRequestException;


public class UserConnectionException extends BadRequestException {

  private static final String DESCRIPTION = "Something went wrong while trying to connect, please re-enter user information";

  public UserConnectionException() {
    super(DESCRIPTION);
  }
}
