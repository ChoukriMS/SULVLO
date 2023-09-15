package ca.ulaval.glo4003.sulvlo.api.mapper;

public class SuccessfulResponse {

  private final String message;

  public SuccessfulResponse(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format("{\n\t\"message\": \"%s\"\n}", message);
  }
}
