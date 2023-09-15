package ca.ulaval.glo4003.sulvlo.api.mapper;

public class ErrorResponse {

  private final int code;
  private final String message;

  public ErrorResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format(
        "{\n\t\"error\": \n\t{\n\t\"code\": \"%d\",\n\t\"message\": \"%s\"\n}\n\t}", code, message);
  }
}
