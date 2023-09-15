package ca.ulaval.glo4003.sulvlo.domain.user.exception;

import jakarta.ws.rs.NotAuthorizedException;

public class InvalidActivationTokenException extends NotAuthorizedException {

  private static final String DESCRIPTION = "Activate your account first to do this action!";

  public InvalidActivationTokenException() {
    super(DESCRIPTION);
  }
}
