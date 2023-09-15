package ca.ulaval.glo4003.sulvlo.domain.bike.exception;

public class BikeReturnNotImplementedException extends UnsupportedOperationException {

  private static final String DESCRIPTION =
      "This type of return is not supported by the application.";

  public BikeReturnNotImplementedException() {
    super(DESCRIPTION);
  }

}
