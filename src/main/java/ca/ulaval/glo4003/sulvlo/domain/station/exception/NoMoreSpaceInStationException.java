package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.BadRequestException;

public class NoMoreSpaceInStationException extends BadRequestException {

  private static final String DESCRIPTION = "The bike cannot be put in the '%s' station because there is no space available.";

  public NoMoreSpaceInStationException(String stationName) {
    super(String.format(DESCRIPTION, stationName));
  }
}
