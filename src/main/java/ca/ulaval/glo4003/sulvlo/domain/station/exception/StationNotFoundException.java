package ca.ulaval.glo4003.sulvlo.domain.station.exception;

import jakarta.ws.rs.NotFoundException;

public class StationNotFoundException extends NotFoundException {

  private static final String DESCRIPTION = "Station '%s' not found.";

  public StationNotFoundException(String stationCode) {
    super(String.format(DESCRIPTION, stationCode));
  }

}
