package ca.ulaval.glo4003.sulvlo.api.validation.exception;

import jakarta.ws.rs.BadRequestException;


public class InvalidPaymentParameterException extends BadRequestException {

  private static final String DESCRIPTION = "One or more of your subscription parameter is wrong, please try again!";

  public InvalidPaymentParameterException() {
    super(DESCRIPTION);
  }
}
