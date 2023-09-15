package ca.ulaval.glo4003.sulvlo.auth.jwt.exceptions;


import jakarta.ws.rs.BadRequestException;

public class InvalidAuthenticationTokenException extends BadRequestException {

  private static final String DESCRIPTION = "Invalid authentification token";

  public InvalidAuthenticationTokenException() {
    super(DESCRIPTION);
  }
}
