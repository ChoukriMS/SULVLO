package ca.ulaval.glo4003.sulvlo.domain.bike.exception;

public class BikeCloneNotSupportedException extends RuntimeException {

  private static final String DESCRIPTION = "Bike cannot be cloned";

  public BikeCloneNotSupportedException() {
    super(DESCRIPTION);
  }
}
