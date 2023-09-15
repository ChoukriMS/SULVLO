package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidReturnInStationException extends BadRequestException {

  private static final String DESCRIPTION = "The bike cannot be returned in the station '%s'.";

  public InvalidReturnInStationException(String stationName) {
    super(String.format(DESCRIPTION, stationName));
  }

}
