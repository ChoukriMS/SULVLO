package ca.ulaval.glo4003.sulvlo.api.validation.exception;

import jakarta.ws.rs.BadRequestException;

public class StudentPassRequiredException extends BadRequestException {

  private static final String DESCRIPTION = "You can not set the payment with school fees if you didn't selected Student Pass.";

  public StudentPassRequiredException() {
    super(DESCRIPTION);
  }
}
