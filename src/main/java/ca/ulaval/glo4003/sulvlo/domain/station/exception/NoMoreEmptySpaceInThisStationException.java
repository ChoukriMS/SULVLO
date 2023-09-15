package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.BadRequestException;

public class NoMoreEmptySpaceInThisStationException extends BadRequestException {

  private static final String DESCRIPTION = "There is no more space in the station '%s'.";

  public NoMoreEmptySpaceInThisStationException(String stationName) {
    super(String.format(DESCRIPTION, stationName));
  }

}
