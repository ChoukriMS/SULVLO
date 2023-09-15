package ca.ulaval.glo4003.sulvlo.auth.jwt.exceptions;


import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class UnauthentificatedUserException {

  private static final String UNAUTHENTICATED_USER_MSG = "Invalid authentication credentials.";

  public UnauthentificatedUserException() {

  }

  public NotAuthorizedException throwException() {
    Response response = Response.status(Status.UNAUTHORIZED).build();
    return new NotAuthorizedException(UNAUTHENTICATED_USER_MSG, response);
  }
}
