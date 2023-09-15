package ca.ulaval.glo4003.sulvlo.auth.jwt.exceptions;


import jakarta.ws.rs.ForbiddenException;

public class PermissionDeniedException extends ForbiddenException {

  private static final String DESCRIPTION = "Insufficient privileges to complete the operation.";

  public PermissionDeniedException() {
    super(DESCRIPTION);
  }
}
