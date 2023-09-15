package ca.ulaval.glo4003.sulvlo.infrastructure.user.exception;

import ca.ulaval.glo4003.sulvlo.infrastructure.exception.ConflictException;

public class UserAlreadyExistsException extends ConflictException {

  private static final String USER_ALREADY_EXIST = "A user with the given email already exist.";

  public UserAlreadyExistsException() {
    super(USER_ALREADY_EXIST);
  }

}
