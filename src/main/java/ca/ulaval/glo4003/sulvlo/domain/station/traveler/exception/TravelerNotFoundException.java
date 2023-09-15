package ca.ulaval.glo4003.sulvlo.domain.station.traveler.exception;

import jakarta.ws.rs.NotFoundException;

public class TravelerNotFoundException extends NotFoundException {

  private static final String DESCRIPTION = "Traveler '%s' not found.";

  public TravelerNotFoundException(String idul) {
    super(String.format(DESCRIPTION, idul));
  }

}
