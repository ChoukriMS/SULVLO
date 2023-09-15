package ca.ulaval.glo4003.sulvlo.infrastructure.user.exception;

import jakarta.ws.rs.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  private static final String USER_DOESNOT_EXIST = "User with the given email does not exist.";

  public UserNotFoundException() {
    super(USER_DOESNOT_EXIST);
  }

}
