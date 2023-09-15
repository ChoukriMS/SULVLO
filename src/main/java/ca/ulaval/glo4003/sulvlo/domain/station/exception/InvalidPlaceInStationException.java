package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidPlaceInStationException extends BadRequestException {

  private static final String DESCRIPTION = "The bike cannot be placed in the station '%s'.";

  public InvalidPlaceInStationException(String stationName) {
    super(String.format(DESCRIPTION, stationName));
  }
}
