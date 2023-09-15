package ca.ulaval.glo4003.sulvlo.infrastructure.exception;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

public class ConflictException extends ClientErrorException {

  public ConflictException(String message) {
    super(message, Response.Status.CONFLICT);
  }

}
