package ca.ulaval.glo4003.sulvlo.api.validation.exception;

import jakarta.ws.rs.BadRequestException;

public class NotFullTimeStudentException extends BadRequestException {

  private static final String DESCRIPTION = "You can not set the payment with school fees, you need to be a full-time student.";

  public NotFullTimeStudentException() {
    super(DESCRIPTION);
  }
}
