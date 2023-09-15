package ca.ulaval.glo4003.sulvlo.infrastructure.exception;

public class DataReadException extends RuntimeException {

  private static final String MESSAGE = "An exception occured during the data reading when the server started.";

  public DataReadException() {
    super(MESSAGE);
  }

}
