package ca.ulaval.glo4003.sulvlo.domain.bike.exception;

import jakarta.ws.rs.NotFoundException;

public class BikeNotFoundException extends NotFoundException {

  private static final String DESCRIPTION = "Bike '%d' not found.";

  public BikeNotFoundException(int bikeLocation) {
    super(String.format(DESCRIPTION, bikeLocation));
  }

}
