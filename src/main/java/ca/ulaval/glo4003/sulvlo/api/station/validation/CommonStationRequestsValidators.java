package ca.ulaval.glo4003.sulvlo.api.station.validation;

import jakarta.ws.rs.BadRequestException;
import java.util.regex.Pattern;

public class CommonStationRequestsValidators {

  private static final String BAD_REQUEST_STATION_CODE_MSG = "Invalid station code.";
  private static final String BAD_REQUEST_BIKE_LOCATION_MSG = "Invalid bike location.";
  private static final String BAD_REQUEST_USER_CODE_MSG = "Invalid user code.";
  private static final String VALID_USER_CODE_PATTERN = "^[0-9]*$";
  private static final String VALID_STATION_CODE_PATTERN = "^[A-Z]*$";
  private static final String VALID_BIKE_LOCATION_PATTERN = "^\\d+$";
  private static final int LENGTH_OF_VALID_USER_CODE = 10;

  public void validateUserCode(String userCode) {
    if (!Pattern.matches(VALID_USER_CODE_PATTERN, userCode.trim())
        || userCode.trim().length() != LENGTH_OF_VALID_USER_CODE) {
      throw new BadRequestException(BAD_REQUEST_USER_CODE_MSG);
    }
  }

  public void validateStationCode(String stationCode) {
    if (stationCode == null || !Pattern.matches(VALID_STATION_CODE_PATTERN, stationCode.trim())) {
      throw new BadRequestException(BAD_REQUEST_STATION_CODE_MSG);
    }
  }

  public void validateBikeLocation(String bikeLocation) {
    if (bikeLocation == null || !Pattern.matches(VALID_BIKE_LOCATION_PATTERN, bikeLocation.trim())
        || isNegativeOrNull(bikeLocation)) {
      throw new BadRequestException(BAD_REQUEST_BIKE_LOCATION_MSG);
    }
  }

  private boolean isNegativeOrNull(String bikeLocation) {
    try {
      return Integer.parseInt(bikeLocation) < 1;
    } catch (Exception e) {
      throw new BadRequestException(BAD_REQUEST_BIKE_LOCATION_MSG);
    }
  }
}
